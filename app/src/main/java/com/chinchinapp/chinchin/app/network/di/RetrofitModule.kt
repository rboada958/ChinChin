package com.chinchinapp.chinchin.app.network.di

import android.content.res.Resources
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.app.di.ChinChinScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = [NetworkModule::class])
class RetrofitModule {
    @Provides
    @ChinChinScope
    fun provideBaseUrl(resources: Resources): String {
        return resources.getString(R.string.base_url)
    }

    @Provides
    @ChinChinScope
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @ChinChinScope
    fun provideRetrofit(okHttpClient: OkHttpClient, url: String, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .baseUrl(url)
            .build()
    }

    @Provides
    @ChinChinScope
    @LoginQualifier
    fun provideRetrofitLogin(@LoginQualifier okHttpClient: OkHttpClient, url: String): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .baseUrl(url)
            .build()
    }


}