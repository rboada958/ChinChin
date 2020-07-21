package com.chinchinapp.chinchin.app.network.di

import com.chinchinapp.chinchin.app.di.ChinChinScope
import com.chinchinapp.chinchin.app.network.api.CryptoApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class])
class ChinChinApiModule {

    @Provides
    @ChinChinScope
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi = retrofit.create(CryptoApi::class.java)

}