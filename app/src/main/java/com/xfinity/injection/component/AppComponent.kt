package com.xfinity.injection.component

import android.app.Application
import android.content.Context
import com.xfinity.data.DataManager
import com.xfinity.injection.ApplicationContext
import com.xfinity.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun apiManager(): DataManager

}