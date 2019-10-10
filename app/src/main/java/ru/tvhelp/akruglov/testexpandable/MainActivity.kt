package ru.tvhelp.akruglov.testexpandable

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import ru.tvhelp.akruglov.testexpandable.ui.control.view.ControlLampScheduleFragment

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)

        if (fragment == null) {
            fragment = ControlLampScheduleFragment()
            fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}

