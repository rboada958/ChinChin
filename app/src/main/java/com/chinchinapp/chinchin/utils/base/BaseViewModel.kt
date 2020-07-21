package com.chinchinapp.chinchin.utils.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chinchinapp.chinchin.app.ChinChinApp
import com.chinchinapp.chinchin.ui.home.mvvm.HomeViewModel
import com.chinchinapp.chinchin.ui.login.mvvm.LoginViewModel
import com.chinchinapp.chinchin.ui.qr.mvvm.QrViewModel
import com.chinchinapp.chinchin.utils.events.EventLiveData

abstract class BaseViewModel : ViewModel(), RemoteErrorEmitter {

    val mutableProgress = MutableLiveData<Int>(View.GONE)
    val mutableVisibility = MutableLiveData<Int>(View.GONE)
    val mutableVisibilityExchange = MutableLiveData<Int>(View.VISIBLE)
    val mutableErrorMessage = MutableLiveData<EventLiveData<String>>()
    val mutableNotifyMessage = MutableLiveData<EventLiveData<String>>()
    val mutableSuccessMessage = MutableLiveData<EventLiveData<String>>()
    val mutableErrorType = MutableLiveData<EventLiveData<ErrorType>>()

    private val injector = ChinChinApp.appInstance.getInjector()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.inject(this)
            is HomeViewModel-> injector.inject(this)
            is QrViewModel -> injector.inject(this)
        }
    }

    override fun onError(errorType: ErrorType) {
        mutableErrorType.postValue(EventLiveData(errorType))
    }

    override fun onError(msg: String) {
        mutableErrorMessage.postValue(EventLiveData(msg))
    }
}