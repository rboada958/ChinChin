package com.chinchinapp.chinchin.ui.qr.mvvm

import com.chinchinapp.chinchin.app.models.CryptoResponse
import com.chinchinapp.chinchin.app.network.api.CryptoApi
import com.chinchinapp.chinchin.utils.base.BaseRemoteRepository
import com.chinchinapp.chinchin.utils.base.RemoteErrorEmitter

class QrRemoteRepository(private var cryptoApi: CryptoApi) :
    BaseRemoteRepository(), QrRepository {

    override suspend fun getCrypto(errorEmitter: RemoteErrorEmitter): CryptoResponse? {
        return safeApiCall(errorEmitter) { cryptoApi.getCrypto() }
    }
}