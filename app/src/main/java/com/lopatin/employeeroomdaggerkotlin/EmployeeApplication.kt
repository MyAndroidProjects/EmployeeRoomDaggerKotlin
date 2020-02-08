package com.lopatin.employeeroomdaggerkotlin

import android.app.Application
import android.content.Context
import com.lopatin.employeeroomdaggerkotlin.di.AppComponent
import com.lopatin.employeeroomdaggerkotlin.di.ApplicationContextModule
import com.lopatin.employeeroomdaggerkotlin.di.DaggerAppComponent

class EmployeeApplication : Application() {
    lateinit var appComponent: AppComponent

    companion object {
        @get:Synchronized
        lateinit var instance: EmployeeApplication
            private set

        fun getApplicationContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        /**
         * для предоставления get- компонентов не обязательно прописывать модуль в билдере,
         * но в модуль ApplicationContextModule надо передать в конструктор context, поэтому его указываем
         */
        appComponent = DaggerAppComponent.builder()
            .applicationContextModule(ApplicationContextModule(this.applicationContext))
            .build()
    }
}