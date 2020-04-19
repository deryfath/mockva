package com.project.daksatest

import android.app.Application
import com.project.daksatest.dagger.component.AppComponent
import com.project.daksatest.dagger.component.DaggerAppComponent
import com.project.daksatest.dagger.module.ApiModule
import com.project.daksatest.dagger.module.AppModule
import com.project.daksatest.dagger.module.NetworkModule

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .networkModule(NetworkModule("https://mockva.daksa.co.id/"))
                    .apiModule(ApiModule())
                    .build()
    }
}