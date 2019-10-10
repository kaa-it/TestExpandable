package ru.tvhelp.akruglov.testexpandable.ui.control.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.tvhelp.akruglov.testexpandable.model.ModemPackage

interface ControlLampScheduleView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLampSchedule(lamp: ModemPackage)
}