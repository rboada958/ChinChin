package com.chinchinapp.chinchin.ui.qr.mvvm

import com.chinchinapp.chinchin.app.models.CryptoResponse
import com.chinchinapp.chinchin.utils.base.RemoteErrorEmitter

interface QrRepository {

    suspend fun getCrypto(errorEmitter: RemoteErrorEmitter) : CryptoResponse?
}