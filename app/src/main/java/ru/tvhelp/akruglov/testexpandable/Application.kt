package ru.tvhelp.akruglov.testexpandable

import ru.tvhelp.akruglov.testexpandable.di.app.ApplicationComponent
import ru.tvhelp.akruglov.testexpandable.di.control.ControlComponent
import ru.tvhelp.akruglov.testexpandable.di.control.modules.ControlModule

class Application : BaseApplication() {

    val component: ApplicationComponent by lazy { initDaggerComponent() }

    private var controlComponent: ControlComponent? = null

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    fun plusControlComponent(): ControlComponent {
        controlComponent = controlComponent ?: component.plusControlComponent(ControlModule())
        return controlComponent!!
    }

    fun clearControlComponent() {
        controlComponent = null
    }
}