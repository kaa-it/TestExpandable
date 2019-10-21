package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_control_lamp_schedule.*
import kotlinx.android.synthetic.main.schedule_parameters_group.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.tvhelp.akruglov.testexpandable.Application
import ru.tvhelp.akruglov.testexpandable.R
import ru.tvhelp.akruglov.testexpandable.model.ModemPackage
import ru.tvhelp.akruglov.testexpandable.ui.control.presenter.ControlLampSchedulePresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ControlLampScheduleFragment:
    MvpAppCompatFragment(),
    ControlLampScheduleView,
    SimpleScheduleParametersFragment.OnParametersChangesListener,
    ComplexScheduleParametersFragment.OnParametersChangesListener {

    override fun onChanged(parameters: ComplexScheduleParametersFragment.Parameters) {
        renderComplexGraph(parameters)
    }

    override fun onChanged(parameters: SimpleScheduleParametersFragment.Parameters) {
        renderGraph(parameters.d1, parameters.p1, parameters.d2, parameters.p2)
    }

    private lateinit var lamp: ModemPackage
    private var currentProfile: Int = 0

    val component by lazy {
        (activity?.application as Application).plusControlComponent()
    }

    private var simpleFragment: SimpleScheduleParametersFragment? = null
    private var complexFragment: ComplexScheduleParametersFragment? = null

    private val dateFormatter = SimpleDateFormat("hh-mm")
    private val calendar = Calendar.getInstance()

    private lateinit var midnight : LimitLine

    @Inject
    @InjectPresenter
    lateinit var presenter: ControlLampSchedulePresenter

    @ProvidePresenter
    fun provideControlLampSchedulePresenter(): ControlLampSchedulePresenter = presenter

    override fun onAttachFragment(childFragment: Fragment) {
        when (childFragment) {
            is SimpleScheduleParametersFragment -> childFragment.callback = this
            is ComplexScheduleParametersFragment -> childFragment.callback = this
        }
    }

    override fun showLampSchedule(lamp: ModemPackage) {
        if (lamp.id == currentProfile) {
            Log.d("CON_LAMP_SCHED_FRG", "Equal: $currentProfile")
            return
        }

        currentProfile = lamp.id

        if (lamp.id > 3) {
            Log.d("CON_LAMP_SCHED_FRG", "Greater profile: ${lamp.id}")
            if (complexFragment == null) {
                complexFragment = ComplexScheduleParametersFragment()
            }
            val parameters = ComplexScheduleParametersFragment.Parameters(
                lamp.lpwm,
                lamp.pwm0,
                lamp.set,
                lamp.time1,
                lamp.pwm1,
                lamp.time2,
                lamp.pwm2,
                lamp.time3,
                lamp.pwm3,
                lamp.time4,
                lamp.pwm4,
                lamp.rise
            )
            complexFragment!!.parameters = parameters
            childFragmentManager.beginTransaction()
                .replace(R.id.fragment_parameters_container, complexFragment!!)
                .commit()
            profiles.setSelection(currentProfile - 1)
            renderComplexGraph(parameters)
            return
        }

        profiles.setSelection(currentProfile - 1)

        this.lamp = lamp

        renderGraph(lamp.d1, lamp.p1, lamp.d2, lamp.p2)

        val fm  = childFragmentManager

        if (simpleFragment == null) {
            simpleFragment = SimpleScheduleParametersFragment()
        }

        simpleFragment!!.parameters = SimpleScheduleParametersFragment.Parameters(
            lamp.d1,
            lamp.p1,
            lamp.d2,
            lamp.p2
        )

        fm.beginTransaction()
            .replace(R.id.fragment_parameters_container, simpleFragment!!)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_control_lamp_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        midnight =  LimitLine(50f, getString(R.string.control_half_of_night))
        midnight.lineColor = Color.RED
        midnight.enableDashedLine(10f, 5f, 0f)

        saveScheduleButton.setOnClickListener {
            //Log.d("CON_LAMP_SCHED_FRG", "Set profile [$currentProfile, ${d1SeekBar.progress}, ${p1SeekBar.progress}, ${d2SeekBar.progress}, ${p2SeekBar.progress}]")
            //presenter.setProfile(currentProfile, d1SeekBar.progress, p1SeekBar.progress, d2SeekBar.progress, p2SeekBar.progress)
        }

        profiles.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d("CON_LAMP_SCHED_FRG", "position:$position id:$id current:$currentProfile")

                if (currentProfile == 0 || currentProfile == position + 1) {
                    Log.d("CON_LAMP_SCHED_FRG", "Command to modem skipped")
                    return
                }

                //currentProfile = position + 1
                presenter.selectProfile(position + 1)
            }

        }

        graph.description = null

        presenter.init()
    }

    private fun generateEntries(d1: Float, p1: Float, d2: Float, p2: Float) : ArrayList<Entry> {

        val x5 = d1 + (100.0f - d2 - 2.0f * d1) / 2.0f
        val x6 = x5 + d2
        val x7 = 100.0f - d1

        val entries = ArrayList<Entry>()

        entries.add(Entry(-10.0f, 0.0f))
        entries.add(Entry(0.0f, 0.0f))
        entries.add(Entry(0.0f, 100.0f))
        entries.add(Entry(d1, 100.0f))
        entries.add(Entry(d1, p1))
        entries.add(Entry(x5, p1))
        entries.add(Entry(x5, p2))
        entries.add(Entry(x6, p2))
        entries.add(Entry(x6, p1))
        entries.add(Entry(x7, p1))
        entries.add(Entry(x7, 100.0f))
        entries.add(Entry(100.0f, 100.0f))
        entries.add(Entry(100.0f, 0.0f))
        entries.add(Entry(110.0f, 0.0f))

        return entries
    }

    private fun renderGraph(d1Val: Int, p1Val: Int, d2Val: Int, p2Val: Int) {

        val d1 = d1Val.toFloat()
        val p1 = p1Val.toFloat()
        val d2 = d2Val.toFloat()
        val p2 = p2Val.toFloat()

        graph.axisRight.removeAllLimitLines()

        if (!graph.xAxis.limitLines.contains(midnight)) {
            graph.xAxis.addLimitLine(midnight)
        }

        val entries = generateEntries(d1, p1, d2, p2)

        if (graph.data == null) {

            val dataSet = LineDataSet(entries, getString(R.string.control_schedule_profile))
            dataSet.color = Color.GREEN
            dataSet.lineWidth = 4f

            graph.data = LineData(dataSet)
        } else {
            graph.data.dataSets[0].clear()
            val dataSet = graph.data.dataSets[0]

            for (entry in entries) {
                dataSet.addEntry(entry)
            }
        }

        if (p1Val == p2Val) {
            val pLimit = LimitLine(p1, "p1, p2")
            pLimit.lineColor = Color.MAGENTA
            pLimit.enableDashedLine(10f, 5f, 0f)

            graph.axisRight.addLimitLine(pLimit)
        } else {
            val p1Limit = LimitLine(p1, "p1")
            p1Limit.lineColor = Color.MAGENTA
            p1Limit.enableDashedLine(10f, 5f, 0f)

            graph.axisRight.addLimitLine(p1Limit)

            val p2Limit = LimitLine(p2, "p2")
            p2Limit.lineColor = Color.MAGENTA
            p2Limit.enableDashedLine(10f, 5f, 0f)

            graph.axisRight.addLimitLine(p2Limit)
        }

        graph.notifyDataSetChanged()

        graph.invalidate()
    }

    private fun toSeconds(time: String): Int {
        calendar.time = dateFormatter.parse(time)
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)
        return hours * 60 * 60 + minutes * 60 + seconds
    }

    private fun adjustTime(time: Int, origin: Int): Int = when {
        time > 12 * 60 * 60 -> time - origin
        else -> time + 24 * 60 * 60 - origin
    }

    private fun generateComplexEntries(
        lpwm: Float,
        set: Float,
        pwm0: Float,
        t1: Float,
        pwm1: Float,
        t2: Float,
        pwm2: Float,
        t3: Float,
        pwm3: Float,
        t4: Float,
        pwm4: Float,
        rise: Float
    ): ArrayList<Entry> {
        val entries = ArrayList<Entry>()

        entries.add(Entry(0.0f, lpwm))
        entries.add(Entry(set, pwm0))

        var lastPWM = pwm0

        when {
            set < t1 -> {
                entries.add(Entry(t1, pwm0))
                entries.add(Entry(t1, pwm1))
                entries.add(Entry(t2, pwm1))
                entries.add(Entry(t2, pwm2))
                lastPWM = pwm2
            }
            set in t1..t2 -> {
                entries.add(Entry(t2, pwm0))
                entries.add(Entry(t2, pwm2))
                lastPWM = pwm2
            }
        }

        when {
            rise < t3 -> {
                entries.add(Entry(rise, lastPWM))
            }
            rise in t3..t4 -> {
                entries.add(Entry(t3, lastPWM))
                entries.add(Entry(t3, pwm3))
                entries.add(Entry(rise, pwm3))
            }
            else -> {
                entries.add(Entry(t3, lastPWM))
                entries.add(Entry(t3, pwm3))
                entries.add(Entry(t4, pwm3))
                entries.add(Entry(t4, pwm4))
                entries.add(Entry(rise, pwm4))
            }
        }

        entries.add(Entry(100f, lpwm))

        return entries
    }

    private fun renderComplexGraph(parameters: ComplexScheduleParametersFragment.Parameters) {
        Log.d("CON_LAMP_SCHED_FRG", parameters.toString())
        //val t0 = toSeconds(time0)
        val setSeconds = toSeconds(parameters.set)
        val origin = setSeconds - 30 * 60 // minus 30 minutes
        val t1Seconds = adjustTime(toSeconds(parameters.time1), origin)
        val t2Seconds = adjustTime(toSeconds(parameters.time2), origin)
        val t3Seconds = adjustTime(toSeconds(parameters.time3), origin)
        val t4Seconds = adjustTime(toSeconds(parameters.time4), origin)
        val riseSeconds = adjustTime(toSeconds(parameters.rise), origin)
        //val tN = adjustTime(toSeconds(timeN), t0)
        val tNSeconds = riseSeconds + 30 * 60 // plus 30 minutes

        // Scaled to percents times
        val t0 = 0f
        val set = (setSeconds - origin) * 100.0f / tNSeconds
        val t1 = t1Seconds * 100.0f / tNSeconds
        val t2 = t2Seconds * 100.0f / tNSeconds
        val t3 = t3Seconds * 100.0f / tNSeconds
        val t4 = t4Seconds * 100.0f / tNSeconds
        val rise = riseSeconds * 100.0f / tNSeconds
        val tN = 100f

        graph.axisRight.removeAllLimitLines()
        graph.xAxis.removeLimitLine(midnight)

        val entries = generateComplexEntries(
            parameters.lpwm.toFloat(),
            set,
            parameters.pwm0.toFloat(),
            t1,
            parameters.pwm1.toFloat(),
            t2,
            parameters.pwm2.toFloat(),
            t3,
            parameters.pwm3.toFloat(),
            t4,
            parameters.pwm4.toFloat(),
            rise
        )

        if (graph.data == null) {

            val dataSet = LineDataSet(entries, getString(R.string.control_schedule_profile))
            dataSet.color = Color.GREEN
            dataSet.lineWidth = 4f

            graph.data = LineData(dataSet)
        } else {
            graph.data.dataSets[0].clear()
            val dataSet = graph.data.dataSets[0]

            for (entry in entries) {
                dataSet.addEntry(entry)
            }
        }

        /*if (p1Val == p2Val) {
            val pLimit = LimitLine(p1, "p1, p2")
            pLimit.lineColor = Color.MAGENTA
            pLimit.enableDashedLine(10f, 5f, 0f)

            graph.axisRight.addLimitLine(pLimit)
        } else {
            val p1Limit = LimitLine(p1, "p1")
            p1Limit.lineColor = Color.MAGENTA
            p1Limit.enableDashedLine(10f, 5f, 0f)

            graph.axisRight.addLimitLine(p1Limit)

            val p2Limit = LimitLine(p2, "p2")
            p2Limit.lineColor = Color.MAGENTA
            p2Limit.enableDashedLine(10f, 5f, 0f)

            graph.axisRight.addLimitLine(p2Limit)
        }*/

        val mv = MyMarkerView(
            activity!!,
            R.layout.custom_marker_view,
            parameters.set,
            tNSeconds
        )
        mv.chartView = graph
        graph.marker = mv

        graph.notifyDataSetChanged()

        graph.invalidate()
    }
}