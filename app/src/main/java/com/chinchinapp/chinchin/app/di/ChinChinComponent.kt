package com.chinchinapp.chinchin.app.di

import com.chinchinapp.chinchin.app.network.api.CryptoApi
import com.chinchinapp.chinchin.app.network.di.ChinChinApiModule
import dagger.Component

@ChinChinScope
@Component(modules = [ChinChinModule::class, ChinChinApiModule::class])
interface ChinChinComponent {

    fun cryptoApi() : CryptoApi

}