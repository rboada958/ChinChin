package com.chinchinapp.chinchin.app.network.interceptor

import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.utils.Commons
import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = Commons.getString(R.string.connectivity_exception)
}
