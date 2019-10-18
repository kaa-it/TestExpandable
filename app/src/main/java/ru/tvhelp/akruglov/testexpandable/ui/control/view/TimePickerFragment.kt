package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatDialogFragment
import java.util.*

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