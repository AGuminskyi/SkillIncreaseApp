package com.idapgroup.artemhuminkiy.skillincreaseapp.app

import android.app.Application
import android.content.Context
import com.idapgroup.artemhuminkiy.skillincreaseapp.app.di.AppComponent
import com.idapgroup.artemhuminkiy.skillincreaseapp.app.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        fun getComponent(context: Context) =
                (context.applicationContext as App).appComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}