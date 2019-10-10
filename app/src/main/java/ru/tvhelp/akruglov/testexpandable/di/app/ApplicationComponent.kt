package ru.tvhelp.akruglov.testexpandable.di.app

import dagger.Component
import ru.tvhelp.akruglov.testexpandable.BaseApplication
import ru.tvhelp.akruglov.testexpandable.di.app.modules.AndroidModule
import ru.tvhelp.akruglov.testexpandable.di.control.ControlComponent
import ru.tvhelp.akruglov.testexpandable.di.control.modules.ControlModule
import javax.inject.Singleton

@Component(modules = arrayOf(AndroidModule::class))
@Singleton
interface ApplicationComponent {

    fun plusControlComponent(controlModule: ControlModule): ControlComponent

    fun inject(app: BaseApplication)
}