package com.chinchinapp.chinchin.ui.home.mvvm

import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chinchinapp.chinchin.utils.UtilityTextWatcher
import com.chinchinapp.chinchin.utils.base.BaseViewModel

class HomeViewModel : BaseViewModel() {

    private val _amount = MutableLiveData<Float>()
    val amount: LiveData<Float> = _amount

    fun amountTextWatcher(): TextWatcher = UtilityTextWatcher {
        if (it.isNotEmpty()) {
            val stringAmount = it.toString()
            _amount.value = stringAmount.toFloat()
        }
    }
}