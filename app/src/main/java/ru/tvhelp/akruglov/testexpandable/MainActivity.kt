package ru.tvhelp.akruglov.testexpandable

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parametersGroup.setOnClickListener {
            if (parametersLayout.visibility == View.GONE) {
                expandableImage.setImageResource(R.drawable.ic_expand_less_black_24dp)
                /*d1Group.visibility = View.VISIBLE
                p1Group.visibility = View.VISIBLE
                d2Group.visibility = View.VISIBLE
                p2Group.visibility = View.VISIBLE
                slideDown(this, d1Group)
                slideDown(this, p1Group)
                slideDown(this, d2Group)
                slideDown(this, p2Group)*/
                parametersLayout.visibility = View.VISIBLE
                slideDown(this, parametersLayout)
            } else {
                expandableImage.setImageResource(R.drawable.ic_expand_more_black_24dp)
                /*slideUp(this, d1Group)
                slideUp(this, p1Group)
                slideUp(this, d2Group)
                slideUp(this, p2Group)
                d1Group.visibility = View.GONE
                p1Group.visibility = View.GONE
                d2Group.visibility = View.GONE
                p2Group.visibility = View.GONE*/
                slideUp(this, parametersLayout)
                parametersLayout.visibility = View.GONE
            }
        }
    }
}
