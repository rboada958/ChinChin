package com.chinchinapp.chinchin.app.network.di

import com.chinchinapp.chinchin.app.network.api.CryptoApi
import com.chinchinapp.chinchin.ui.qr.mvvm.QrRemoteRepository
import com.chinchinapp.chinchin.ui.qr.mvvm.QrRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideCrypto(cryptoApi: CryptoApi): QrRepository =
        QrRemoteRepository(cryptoApi)
}