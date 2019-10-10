package ru.tvhelp.akruglov.testexpandable

import android.app.Application
import ru.tvhelp.akruglov.testexpandable.di.app.ApplicationComponent
import ru.tvhelp.akruglov.testexpandable.di.app.DaggerApplicationComponent
import ru.tvhelp.akruglov.testexpandable.di.app.modules.AndroidModule

abstract class BaseApplication: Application() {
    protected fun initDaggerComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder().androidModule(AndroidModule(this)).build()
    }
}