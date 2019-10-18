package ru.tvhelp.akruglov.testexpandable.ui.control.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.tvhelp.akruglov.testexpandable.model.ModemPackage
import ru.tvhelp.akruglov.testexpandable.ui.control.view.ControlLampScheduleView

@InjectViewState
class ControlLampSchedulePresenter: MvpPresenter<ControlLampScheduleView>() {

    private val lamp = ModemPackage(
        id = 1,
        d1 = 10,
        p1 = 50,
        d2 = 50,
        p2 = 25,
        lpwm = 10,
        pwm0 = 100,
        time1 = "20-00",
        pwm1 = 70,
        time2 = "21-30",
        pwm2 = 50,
        time3 = "03-00",
        pwm3 = 70,
        time4 = "04-00",
        pwm4 = 100,
        rise = "04-30",
        set = "15-28"
    )

    fun init() {
        viewState.showLampSchedule(lamp)
    }

    fun selectProfile(profile: Int) {
        viewState.showLampSchedule(lamp.copy(id = profile))
    }
}