package com.example.pmacademyandroid_metelov_m24

import android.app.Application
import com.example.pmacademyandroid_metelov_m24.app.AppComponent
import com.example.pmacademyandroid_metelov_m24.app.AppModule
import com.example.pmacademyandroid_metelov_m24.app.DaggerAppComponent

class AppApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}