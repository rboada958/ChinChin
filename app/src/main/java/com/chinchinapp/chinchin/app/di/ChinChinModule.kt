package com.chinchinapp.chinchin.app.di

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.chinchinapp.chinchin.app.ChinChinApp
import dagger.Module
import dagger.Provides

@Module
class ChinChinModule(val app: ChinChinApp) {
    @Provides
    @ChinChinScope
    fun provideApp(): ChinChinApp = app

    @Provides
    @ChinChinScope
    fun provideResource(): Resources {
        return app.resources
    }

    @Provides
    @ChinChinScope
    fun provideApplicationContext(): Context {
        return app
    }

    @Provides
    @ChinChinScope
    fun provideAppComponent(appComponent: ChinChinComponent): ChinChinComponent {
        return appComponent
    }

    @Provides
    @ChinChinScope
    fun provideAssets(): AssetManager {
        return app.assets
    }
}