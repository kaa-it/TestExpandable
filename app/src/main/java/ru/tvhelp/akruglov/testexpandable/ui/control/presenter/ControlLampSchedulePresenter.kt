package ru.tvhelp.akruglov.testexpandable.ui.control.presenter

import moxy.InjectViewState
import moxy.MvpPresenter
import ru.tvhelp.akruglov.testexpandable.model.ModemPackage
import ru.tvhelp.akruglov.testexpandable.ui.control.view.ControlLampScheduleView

@InjectViewState
class ControlLampSchedulePresenter: MvpPresenter<ControlLampScheduleView>() {

    private val lamp = ModemPackage(
        "",
        "",
        1,
        10,
        50,
        50,
        25,
        10,
        100,
        "20-00",
        70,
        "21-30",
        50,
        "03-00",
        70,
        "04-00",
        100
    )

    fun init() {
        viewState.showLampSchedule(lamp)
    }

    fun selectProfile(profile: Int) {
        viewState.showLampSchedule(lamp.copy(id = profile))
    }
}