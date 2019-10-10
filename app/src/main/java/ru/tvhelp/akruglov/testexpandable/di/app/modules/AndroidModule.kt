package ru.tvhelp.akruglov.testexpandable.di.app.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.tvhelp.akruglov.testexpandable.BaseApplication
import javax.inject.Singleton

@Module
class AndroidModule(private val application: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application
}