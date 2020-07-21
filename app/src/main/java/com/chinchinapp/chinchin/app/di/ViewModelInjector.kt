package com.chinchinapp.chinchin.app.di

import com.chinchinapp.chinchin.app.network.di.RepositoriesModule
import com.chinchinapp.chinchin.ui.home.mvvm.HomeViewModel
import com.chinchinapp.chinchin.ui.login.mvvm.LoginViewModel
import com.chinchinapp.chinchin.ui.qr.mvvm.QrViewModel
import dagger.Component

@ViewModelScope
@Component(dependencies = [ChinChinComponent::class], modules = [RepositoriesModule::class])
interface ViewModelInjector {

    fun inject(loginViewModel: LoginViewModel)
    fun inject(homeViewModel: HomeViewModel)
    fun inject(qrViewModel: QrViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun appComponent(appComponent: ChinChinComponent): Builder
    }
}