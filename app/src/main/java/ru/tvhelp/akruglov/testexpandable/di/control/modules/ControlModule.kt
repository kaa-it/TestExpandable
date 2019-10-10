package ru.tvhelp.akruglov.testexpandable.di.control.modules

import dagger.Module
import dagger.Provides
import ru.tvhelp.akruglov.testexpandable.di.scopes.ControlScope
import ru.tvhelp.akruglov.testexpandable.ui.control.presenter.ControlLampSchedulePresenter

@Module
class ControlModule {

    @Provides
    @ControlScope
    fun provideControlLampSchedulePresenter(): ControlLampSchedulePresenter = ControlLampSchedulePresenter()
}