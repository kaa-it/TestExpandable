package ru.tvhelp.akruglov.testexpandable.di.control

import dagger.Subcomponent
import ru.tvhelp.akruglov.testexpandable.di.control.modules.ControlModule
import ru.tvhelp.akruglov.testexpandable.di.scopes.ControlScope
import ru.tvhelp.akruglov.testexpandable.ui.control.view.ControlLampScheduleFragment

@Subcomponent(modules = arrayOf(ControlModule::class))
@ControlScope
interface ControlComponent {
    fun inject(controlLampScheduleFragment: ControlLampScheduleFragment)
}