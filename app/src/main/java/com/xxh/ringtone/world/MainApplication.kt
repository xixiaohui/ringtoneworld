package com.xxh.ringtone.world

import android.app.Application



class MainApplication: Application() {


    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidLogger()
//            androidContext(this@MainApplication)
//            fragmentFactory()
//            modules(listOf(DatabaseModule, AppModule))
//        }

    }

}