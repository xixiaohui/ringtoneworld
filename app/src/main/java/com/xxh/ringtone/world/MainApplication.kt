package com.xxh.ringtone.world

import android.app.Application
import com.xxh.ringtone.world.di.module.AppModule
import com.xxh.ringtone.world.di.module.DatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(DatabaseModule, AppModule))
        }
    }

}