package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFragment(initTime: String): AppCompatDialogFragment(), TimePickerDialog.OnTimeSetListener {

    var hour: Int
        private set

    var minute: Int
        private set

    var callback: (String) -> Unit = {}

    init {
        val dateFormatter = SimpleDateFormat("hh-mm")
        val t = dateFormatter.parse(initTime)
        val c = Calendar.getInstance()
        c.time = t
        hour = c.get(Calendar.HOUR_OF_DAY)
        minute = c.get(Calendar.MINUTE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val t = "%02d".format(hourOfDay) + "-" + "%02d".format(minute)
        callback(t)
    }
}