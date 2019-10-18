package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_complex_schedule_parameters.*
import kotlinx.android.synthetic.main.fragment_simple_schedule_parameters.*
import kotlinx.android.synthetic.main.schedule_parameters_group.*
import ru.tvhelp.akruglov.testexpandable.R
import ru.tvhelp.akruglov.testexpandable.slideDown
import ru.tvhelp.akruglov.testexpandable.slideUp
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ComplexScheduleParametersFragment: Fragment() {

    data class Parameters(
        var lpwm: Int = 0,
        var pwm0: Int = 0,
        var set: String = "",
        var time1: String = "",
        var pwm1: Int = 0,
        var time2: String = "",
        var pwm2: Int = 0,
        var time3: String = "",
        var pwm3: Int = 0,
        var time4: String = "",
        var pwm4: Int = 0,
        var rise: String = ""
    )

    internal var callback: OnParametersChangesListener? = null

    interface OnParametersChangesListener {
        fun onChanged(parameters: Parameters)
    }

    var parameters: Parameters = Parameters()
        set(value) {
            if (pwm0SeekBar != null) {
                pwm0SeekBar.progress = value.pwm0
                pwm1SeekBar.progress = value.pwm1
                pwm2SeekBar.progress = value.pwm2
                pwm3SeekBar.progress = value.pwm3
                pwm4SeekBar.progress = value.pwm4
            }

            field = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_complex_schedule_parameters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parametersListener = OnClickListener {
            if (pwm0Group.visibility == View.GONE) {
                expandableImage.setImageResource(R.drawable.ic_expand_less_black_24dp)
                pwm0Group.visibility = View.VISIBLE
                pwm1Group.visibility = View.VISIBLE
                pwm2Group.visibility = View.VISIBLE
                pwm3Group.visibility = View.VISIBLE
                pwm4Group.visibility = View.VISIBLE
                time1Button.visibility = View.VISIBLE
                time2Button.visibility = View.VISIBLE
                time3Button.visibility = View.VISIBLE
                time4Button.visibility = View.VISIBLE
                slideDown(activity as Context, pwm0Group)
                slideDown(activity as Context, pwm1Group)
                slideDown(activity as Context, pwm2Group)
                slideDown(activity as Context, pwm3Group)
                slideDown(activity as Context, pwm4Group)
                slideDown(activity as Context, time1Button)
                slideDown(activity as Context, time2Button)
                slideDown(activity as Context, time3Button)
                slideDown(activity as Context, time4Button)
            } else {
                expandableImage.setImageResource(R.drawable.ic_expand_more_black_24dp)
                slideUp(activity as Context, pwm0Group)
                slideUp(activity as Context, pwm1Group)
                slideUp(activity as Context, pwm2Group)
                slideUp(activity as Context, pwm3Group)
                slideUp(activity as Context, pwm4Group)
                slideUp(activity as Context, time1Button)
                slideUp(activity as Context, time2Button)
                slideUp(activity as Context, time3Button)
                slideUp(activity as Context, time4Button)
                pwm0Group.visibility = View.GONE
                pwm1Group.visibility = View.GONE
                pwm2Group.visibility = View.GONE
                pwm3Group.visibility = View.GONE
                pwm4Group.visibility = View.GONE
                time1Button.visibility = View.GONE
                time2Button.visibility = View.GONE
                time3Button.visibility = View.GONE
                time4Button.visibility = View.GONE
            }
        }

        parametersGroup.setOnClickListener(parametersListener)
        expandableImage.setOnClickListener(parametersListener)

        pwm0SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pwm0Value.text = progress.toString()
                if (fromUser) {
                    parameters.pwm0 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        pwm1SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pwm1Value.text = progress.toString()
                if (fromUser) {
                    parameters.pwm1 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        pwm2SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pwm2Value.text = progress.toString()
                if (fromUser) {
                    parameters.pwm2 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        pwm3SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pwm3Value.text = progress.toString()
                if (fromUser) {
                    parameters.pwm3 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        pwm4SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pwm4Value.text = progress.toString()
                if (fromUser) {
                    parameters.pwm4 = progress
                    callback?.onChanged(parameters)
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
            time2Button.text = "TIME2: $it"
        } }

        time3Button.setOnClickListener { showTimePickerDialog {
            time3Button.text = "TIME3: $it"
        } }

        time4Button.setOnClickListener { showTimePickerDialog {
            time4Button.text = "TIME4: $it"
        } }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pwm0SeekBar.progress = parameters.pwm0
        pwm1SeekBar.progress = parameters.pwm1
        pwm2SeekBar.progress = parameters.pwm2
        pwm3SeekBar.progress = parameters.pwm3
        pwm4SeekBar.progress = parameters.pwm4
    }

    fun showTimePickerDialog(f: (String)->Unit) {
        val timePicker = TimePickerFragment()

        timePicker.callback = f

        timePicker.show(childFragmentManager, "timePicker")
    }
}