package ru.tvhelp.akruglov.testexpandable.ui.control.view

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.custom_marker_view.view.*
import java.text.SimpleDateFormat
import java.util.*

class MyMarkerView(
    context: Context,
    layoutResource: Int,
    val origin: String,
    val tNSeconds: Int
    ): MarkerView(context, layoutResource) {

    val dateFormatter = SimpleDateFormat("hh-mm")
    val calendar = Calendar.getInstance()

    override fun refreshContent(e: Entry?, highlight: Highlight?) {



        markerContent.text = toTime(e!!.x) + " / " + "%.2f".format(e!!.y)

        super.refreshContent(e, highlight)
    }

    private fun toTime(time: Float) : String {
        val t0 = dateFormatter.parse(origin)
        val timeSeconds = (time * tNSeconds / 100f).toInt()

        calendar.time = t0

        // adjust to t0 from set
        calendar.add(Calendar.SECOND, - 30 * 60)

        // shift to time
        calendar.add(Calendar.SECOND, timeSeconds)

        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        return "%02d".format(hours) + "-" + "%02d".format(minutes)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(- width / 2.0f, -height.toFloat())
    }
}