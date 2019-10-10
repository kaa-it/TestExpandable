package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_control_lamp_schedule.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.tvhelp.akruglov.testexpandable.Application
import ru.tvhelp.akruglov.testexpandable.R
import ru.tvhelp.akruglov.testexpandable.model.ModemPackage
import ru.tvhelp.akruglov.testexpandable.ui.control.presenter.ControlLampSchedulePresenter
import java.util.ArrayList
import javax.inject.Inject

class ControlLampScheduleFragment: MvpAppCompatFragment(), ControlLampScheduleView {

    private lateinit var lamp: ModemPackage
    private var currentProfile: Int = 0

    val component by lazy {
        (activity?.application as Application).plusControlComponent()
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: ControlLampSchedulePresenter

    @ProvidePresenter
    fun provideControlLampSchedulePresenter(): ControlLampSchedulePresenter = presenter

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

    override fun showLampSchedule(lamp: ModemPackage) {
        if (lamp.id == currentProfile) {
            Log.d("CON_LAMP_SCHED_FRG", "Equal: $currentProfile")
            return
        }

        currentProfile = lamp.id

        if (lamp.id > 3) {
            Log.d("CON_LAMP_SCHED_FRG", "Greater profile: ${lamp.id}")
            return
        }

        profiles.setSelection(currentProfile - 1)

        // TODO: SeekBar update

        this.lamp = lamp

        renderGraph(lamp.d1, lamp.p1, lamp.d2, lamp.p2)
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

        val midnight = LimitLine(50f, getString(R.string.control_half_of_night))
        midnight.lineColor = Color.RED
        midnight.enableDashedLine(10f, 5f, 0f)

        graph.xAxis.addLimitLine(midnight)

        presenter.init()
    }
}