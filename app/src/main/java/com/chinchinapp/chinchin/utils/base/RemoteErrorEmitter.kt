package com.chinchinapp.chinchin.utils.base

interface RemoteErrorEmitter {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
}