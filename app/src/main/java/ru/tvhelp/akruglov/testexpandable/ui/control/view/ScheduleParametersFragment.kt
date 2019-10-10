package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_schedule_parameters.*
import ru.tvhelp.akruglov.testexpandable.R
import ru.tvhelp.akruglov.testexpandable.slideDown
import ru.tvhelp.akruglov.testexpandable.slideUp
import java.util.*

class ScheduleParametersFragment: Fragment() {

    internal var callback: OnParametersChangesListener? = null

    interface OnParametersChangesListener {
        fun onChanged(d1: Int, p1: Int, d2: Int, p2: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_schedule_parameters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parametersGroup.setOnClickListener {
            if (d1Group.visibility == View.GONE) {
                expandableImage.setImageResource(R.drawable.ic_expand_less_black_24dp)
                d1Group.visibility = View.VISIBLE
                p1Group.visibility = View.VISIBLE
                d2Group.visibility = View.VISIBLE
                p2Group.visibility = View.VISIBLE
                pwm0Group.visibility = View.VISIBLE
                pwm1Group.visibility = View.VISIBLE
                pwm2Group.visibility = View.VISIBLE
                pwm3Group.visibility = View.VISIBLE
                pwm4Group.visibility = View.VISIBLE
                time1Button.visibility = View.VISIBLE
                time2Button.visibility = View.VISIBLE
                time3Button.visibility = View.VISIBLE
                time4Button.visibility = View.VISIBLE
                slideDown(activity as Context, d1Group)
                slideDown(activity as Context, p1Group)
                slideDown(activity as Context, d2Group)
                slideDown(activity as Context, p2Group)
                slideDown(activity as Context, pwm0Group)
                slideDown(activity as Context, pwm1Group)
                slideDown(activity as Context, pwm2Group)
                slideDown(activity as Context, pwm3Group)
                slideDown(activity as Context, pwm4Group)
                slideDown(activity as Context, time1Button)
                slideDown(activity as Context, time2Button)
                slideDown(activity as Context, time3Button)
                slideDown(activity as Context, time4Button)
                //slideDown(this, saveScheduleButton)
                /*parametersLayout.visibility = View.VISIBLE
                slideDown(this, parametersLayout)*/
            } else {
                expandableImage.setImageResource(R.drawable.ic_expand_more_black_24dp)
                slideUp(activity as Context, p2Group)
                slideUp(activity as Context, d2Group)
                slideUp(activity as Context, p1Group)
                slideUp(activity as Context, d1Group)
                slideUp(activity as Context, pwm0Group)
                slideUp(activity as Context, pwm1Group)
                slideUp(activity as Context, pwm2Group)
                slideUp(activity as Context, pwm3Group)
                slideUp(activity as Context, pwm4Group)
                slideUp(activity as Context, time1Button)
                slideUp(activity as Context, time2Button)
                slideUp(activity as Context, time3Button)
                slideUp(activity as Context, time4Button)
                //slideUp(this, saveScheduleButton)
                p2Group.visibility = View.GONE
                d2Group.visibility = View.GONE
                p1Group.visibility = View.GONE
                d1Group.visibility = View.GONE
                pwm0Group.visibility = View.GONE
                pwm1Group.visibility = View.GONE
                pwm2Group.visibility = View.GONE
                pwm3Group.visibility = View.GONE
                pwm4Group.visibility = View.GONE
                time1Button.visibility = View.GONE
                time2Button.visibility = View.GONE
                time3Button.visibility = View.GONE
                time4Button.visibility = View.GONE
                /*slideUp(this, parametersLayout)
                parametersLayout.visibility = View.GONE*/
            }
        }

        p1SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                p1Value.text = progress.toString()
                if (fromUser) {
                    callback?.onChanged(d1Value.text.toString().toInt(),
                        progress,
                        d2Value.text.toString().toInt(),
                        p2Value.text.toString().toInt())
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        time1Button.setOnClickListener { showTimePickerDialog {
            time1Button.text = "TIME1: $it"
        } }

        time2Button.setOnClickListener { showTimePickerDialog {
            time1Button.text = "TIME2: $it"
        } }

        time3Button.setOnClickListener { showTimePickerDialog {
            time1Button.text = "TIME3: $it"
        } }

        time4Button.setOnClickListener { showTimePickerDialog {
            time1Button.text = "TIME4: $it"
        } }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val d1 = 10
        val p1 = 50
        val d2 = 50
        val p2 = 25

        p1SeekBar.progress = p1
        p2SeekBar.progress = p2
        d1SeekBar.progress = d1
        d2SeekBar.progress = d2
    }

    fun showTimePickerDialog(f: (String)->Unit) {
        val timePicker = TimePickerFragment()

        timePicker.callback = f

        timePicker.show(childFragmentManager, "timePicker")
    }
}

class TimePickerFragment: AppCompatDialogFragment(), TimePickerDialog.OnTimeSetListener {

    var time: String
        private set

    var callback: (String) -> Unit = {}

    init {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        time = "$hour:$minute"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parts = time.split(":")

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, parts[0].toInt(), parts[1].toInt(), DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        callback("$hourOfDay:$minute")
    }
}