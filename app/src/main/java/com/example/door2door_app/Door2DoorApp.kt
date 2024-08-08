package com.example.door2door_app

import android.app.Application
import com.example.door2door_app.login.di.loginModule
import com.example.door2door_app.networking.di.networkingModule
import com.example.door2door_app.preferences.di.preferencesModule
import com.example.door2door_app.register.di.registerModule
import com.example.door2door_app.splash.di.splashModule
import com.example.door2door_app.user.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Door2DoorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()

            androidContext(this@Door2DoorApp)

            modules(
                networkingModule,
                preferencesModule,
                loginModule,
                registerModule,
                userModule,
                splashModule,
            )
        }
    }
}