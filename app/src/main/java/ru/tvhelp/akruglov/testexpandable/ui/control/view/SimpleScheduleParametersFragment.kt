package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_simple_schedule_parameters.*
import kotlinx.android.synthetic.main.schedule_parameters_group.*
import ru.tvhelp.akruglov.testexpandable.R
import ru.tvhelp.akruglov.testexpandable.slideDown
import ru.tvhelp.akruglov.testexpandable.slideUp

class SimpleScheduleParametersFragment: Fragment() {

    data class Parameters(
        var d1: Int = 0,
        var p1: Int = 0,
        var d2: Int = 0,
        var p2: Int = 0
    )

    internal var callback: OnParametersChangesListener? = null

    interface OnParametersChangesListener {
        fun onChanged(parameters: Parameters)
    }

    var parameters: Parameters = Parameters()
        set(value) {
            if (p1SeekBar != null) {
                p1SeekBar.progress = value.p1
                p2SeekBar.progress = value.p2
                d1SeekBar.progress = value.d1
                d2SeekBar.progress = value.d2
            }

            field = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_simple_schedule_parameters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parametersListener = View.OnClickListener {
            if (d1Group.visibility == View.GONE) {
                expandableImage.setImageResource(R.drawable.ic_expand_less_black_24dp)
                d1Group.visibility = View.VISIBLE
                p1Group.visibility = View.VISIBLE
                d2Group.visibility = View.VISIBLE
                p2Group.visibility = View.VISIBLE
                slideDown(activity as Context, d1Group)
                slideDown(activity as Context, p1Group)
                slideDown(activity as Context, d2Group)
                slideDown(activity as Context, p2Group)
            } else {
                expandableImage.setImageResource(R.drawable.ic_expand_more_black_24dp)
                slideUp(activity as Context, p2Group)
                slideUp(activity as Context, d2Group)
                slideUp(activity as Context, p1Group)
                slideUp(activity as Context, d1Group)
                p2Group.visibility = View.GONE
                d2Group.visibility = View.GONE
                p1Group.visibility = View.GONE
                d1Group.visibility = View.GONE
            }
        }

        parametersGroup.setOnClickListener(parametersListener)
        expandableImage.setOnClickListener(parametersListener)

        d1SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                d1Value.text = progress.toString()
                if (fromUser) {
                    parameters.d1 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        p1SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                p1Value.text = progress.toString()
                if (fromUser) {
                    parameters.p1 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        d2SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                d2Value.text = progress.toString()
                if (fromUser) {
                    parameters.d2 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        p2SeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                p2Value.text = progress.toString()
                if (fromUser) {
                    parameters.p2 = progress
                    callback?.onChanged(parameters)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        p1SeekBar.progress = parameters.p1
        p2SeekBar.progress = parameters.p2
        d1SeekBar.progress = parameters.d1
        d2SeekBar.progress = parameters.d2
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }
}

