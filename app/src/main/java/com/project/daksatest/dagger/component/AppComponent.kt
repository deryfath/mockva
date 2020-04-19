package com.project.daksatest.dagger.component

import com.project.daksatest.module.MainActivity
import com.project.daksatest.dagger.module.ApiModule
import com.project.daksatest.dagger.module.AppModule
import com.project.daksatest.dagger.module.NetworkModule
import com.project.daksatest.module.account.AccountFragment
import com.project.daksatest.module.auth.login.LoginActivity
import com.project.daksatest.module.history.HistoryFragment
import com.project.daksatest.module.main.HomeFragment
import com.project.daksatest.module.splash.SplashActivity
import com.project.daksatest.module.transfer.TransferFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        ApiModule::class
))

interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(homeFragment: HomeFragment)
    fun inject(transferFragment: TransferFragment)
    fun inject(historyFragment: HistoryFragment)
    fun inject(accountFragment: AccountFragment)

}