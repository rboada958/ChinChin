package com.chinchinapp.chinchin.ui.qr.mvvm

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chinchinapp.chinchin.app.models.DataItem
import com.chinchinapp.chinchin.utils.Constans
import com.chinchinapp.chinchin.utils.base.BaseViewModel
import com.chinchinapp.chinchin.utils.manager.PreferenceManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class QrViewModel : BaseViewModel() {

    @Inject
    lateinit var qrRepository: QrRepository

    private val _amount = MutableLiveData<String>()
    val amount: LiveData<String> = _amount

    private val _currency = MutableLiveData<String>()
    val currency: LiveData<String> = _currency


    private val _totalAmount = MutableLiveData<String>()
    val totalAmount: LiveData<String> = _totalAmount

    private val _exchange = MutableLiveData<String>()
    val exchange: LiveData<String> = _exchange

    private val _qrData =
        MutableLiveData<String>(null)
    val qrData: LiveData<String> = _qrData

    private val _cryptoAll = MutableLiveData<List<DataItem?>?>(null)
    val cryptoAll: LiveData<List<DataItem?>?> = _cryptoAll

    fun exchageRate(amount: Float, coin: String?) {
        PreferenceManager.getInstance().setCryptos(true)

        if (coin.equals("api")) {
            mutableVisibility.postValue(View.VISIBLE)
            mutableVisibilityExchange.postValue(View.GONE)

            viewModelScope.launch {
                mutableProgress.postValue(View.VISIBLE)

                val listCrypto = qrRepository.getCrypto(this@QrViewModel)

                mutableProgress.postValue(View.GONE)

                if (listCrypto != null) {
                    Log.e("----->", listCrypto.code!!)
                    _cryptoAll.postValue(listCrypto.data!!)
                }
            }
        } else {
            mutableVisibility.postValue(View.GONE)
            mutableVisibilityExchange.postValue(View.VISIBLE)
        }

        _amount.value = "$amount BS"
        _currency.value = coin

        when (coin) {
            "BTC" -> {
                _totalAmount.value = "${(amount / (Constans.BTC.toFloat() * Constans.USD.toFloat()))} BTC"
                _exchange.value = "${Constans.BTC} USD"
            }
            "USD" -> {
                _totalAmount.value = "${(amount / Constans.USD.toFloat())} USD"
                _exchange.value = "${Constans.USD} BS"
            }
            "EUR" -> {
                _totalAmount.value = "${(amount / Constans.EUR.toFloat())} EUR"
                _exchange.value = "${Constans.EUR} BS"
            }
            "ETH" -> {
                _totalAmount.value = "${(amount / (Constans.ETH.toFloat() * Constans.USD.toFloat()))} ETH"
                _exchange.value = "${Constans.ETH} USD"
            }
            "PTR" -> {
                _totalAmount.value = "${(amount / (Constans.PTR.toFloat() * Constans.USD.toFloat()))} PTR"
                _exchange.value = "${Constans.PTR} USD"
            }
            else -> {
                _totalAmount.value = amount.toString()
                _exchange.value = Constans.BS
            }
        }

        _qrData.postValue(totalAmount.value)
    }
}