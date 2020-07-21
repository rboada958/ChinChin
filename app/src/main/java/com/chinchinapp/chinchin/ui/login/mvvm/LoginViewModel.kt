package com.chinchinapp.chinchin.ui.login.mvvm

import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.utils.Commons
import com.chinchinapp.chinchin.utils.PasswordValidator
import com.chinchinapp.chinchin.utils.UtilityTextWatcher
import com.chinchinapp.chinchin.utils.base.BaseViewModel
import com.chinchinapp.chinchin.utils.enums.Status
import com.chinchinapp.chinchin.utils.events.EventLiveData

class LoginViewModel : BaseViewModel() {

    private val _emailValue = MutableLiveData<String>("")
    val emailValue: LiveData<String> = _emailValue

    private val _passwordValue = MutableLiveData<String>("")
    val passwordValue: LiveData<String> = _passwordValue

    private val _enableButton = MutableLiveData<String>("")
    val enableButton: LiveData<String> = _enableButton

    private val passwordValidator = PasswordValidator()

    fun emailTextWatcher(): TextWatcher = UtilityTextWatcher {
        if (it.isNotEmpty()) {
            _emailValue.postValue(it.toString())

            if (passwordValue.value.isNullOrEmpty() && emailValue.value.isNullOrEmpty())
                _enableButton.postValue(Status.DISABLE.name)
            else
                _enableButton.postValue(Status.ENABLE.name)

        } else
            _enableButton.postValue(Status.DISABLE.name)
    }

    fun passwordTextWatcher(): TextWatcher = UtilityTextWatcher {
        if (it.isNotEmpty()) {
            _passwordValue.postValue(it.toString())

            if (passwordValue.value.isNullOrEmpty() && emailValue.value.isNullOrEmpty())
                _enableButton.postValue(Status.DISABLE.name)
            else _enableButton.postValue(Status.ENABLE.name)

        } else _enableButton.postValue(Status.DISABLE.name)
    }

    fun login() {

        if (enableButton.value == Status.ENABLE.name) {
            if (!Commons.isValidEmail(emailValue.value!!)) {
                mutableErrorMessage.value =
                    EventLiveData(Commons.getString(R.string.text_required_email))
                return
            }

            if (passwordValue.value.isNullOrEmpty()) {
                mutableErrorMessage.value =
                    EventLiveData(Commons.getString(R.string.text_required_password))
                return
            }

            if (!passwordValidator.validate(passwordValue.value!!)) {
                mutableErrorMessage.value =
                    EventLiveData(Commons.getString(R.string.text_verify_pass))
                return
            }

            if (emailValue.value == "chinchin@gmail.com" && passwordValue.value == "1234A@")
                mutableSuccessMessage.value = EventLiveData("success")
            else {
                mutableErrorMessage.value =
                    EventLiveData(Commons.getString(R.string.login_failed))
                return
            }
        }
    }
}