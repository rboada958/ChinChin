package com.chinchinapp.chinchin.app.network.di

import android.content.Context
import android.os.Build
import com.chinchinapp.chinchin.app.ChinChinApp
import com.chinchinapp.chinchin.app.di.ChinChinScope
import com.chinchinapp.chinchin.app.network.interceptor.ConnectivityInterceptor
import com.chinchinapp.chinchin.utils.manager.CustomCookieManager
import com.chinchinapp.chinchin.utils.manager.SessionManager
import com.franmontiel.persistentcookiejar.BuildConfig
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    companion object {
        const val OK_HTTP_CACHE = "okhttp_cache"
    }

    @Provides
    @ChinChinScope
    fun provideinterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @ChinChinScope
    fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, 10*1000*1000) // 10MB Cache
    }

    @Provides
    @ChinChinScope
    fun provideCacheFile(context: Context): File {
        return File(context.cacheDir, OK_HTTP_CACHE)
    }

    @Provides
    @ChinChinScope
    fun provideCookieManager(context: Context): CustomCookieManager = CustomCookieManager(context)

    @Provides
    @ChinChinScope
    fun provideCookieJar(customCookieManager: CustomCookieManager): PersistentCookieJar =
        PersistentCookieJar(SetCookieCache(), customCookieManager)

    @Provides
    @ChinChinScope
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache,
                            cookieJar: PersistentCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ConnectivityInterceptor(ChinChinApp.appContext))
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                    .addHeader(
                        "User-Agent",
                        "ChinChin-ANDROID " + " BUILD VERSION: " + BuildConfig.VERSION_NAME + " SMARTPHONE: " + Build.MODEL + " ANDROID VERSION: " + Build.VERSION.RELEASE
                    )
                    .addHeader("Content-Type", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .readTimeout(80, TimeUnit.SECONDS)
            .connectTimeout(80, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

    @Provides
    @ChinChinScope
    @LoginQualifier
    fun provideOkHttpClientLogin(loggingInterceptor: HttpLoggingInterceptor, cache: Cache,
                                 cookieJar: PersistentCookieJar): OkHttpClient {
        return getOkHttpClient(loggingInterceptor, cache, cookieJar)
    }

    private fun getOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache,
                                cookieJar: PersistentCookieJar): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ConnectivityInterceptor(ChinChinApp.appContext))
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "User-Agent",
                        "ChinChin-ANDROID" + " BUILD VERSION: " + BuildConfig.VERSION_NAME + " SMARTPHONE: " + Build.MODEL + " ANDROID VERSION: " + Build.VERSION.RELEASE
                    )
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .readTimeout(80, TimeUnit.SECONDS)
            .connectTimeout(80, TimeUnit.SECONDS)
            .cache(cache)
            .build()
    }

}