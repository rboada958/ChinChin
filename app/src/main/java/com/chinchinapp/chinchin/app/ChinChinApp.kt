package com.chinchinapp.chinchin.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.chinchinapp.chinchin.app.di.*

class ChinChinApp :  Application(){

    val appComponent: ChinChinComponent by lazy {
        initComponent()
    }

    private val viewModelInjector: ViewModelInjector by lazy {
        DaggerViewModelInjector
            .builder()
            .appComponent(appComponent)
            .build()
    }

    companion object {
        lateinit var appInstance: ChinChinApp
        val appContext: Context by lazy {
            appInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initComponent(): ChinChinComponent =
        DaggerChinChinComponent
            .builder()
            .chinChinModule(ChinChinModule(appInstance))
            .build()

    fun getInjector(): ViewModelInjector = viewModelInjector
}