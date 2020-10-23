package com.xxh.ringtone.world.di.module

import android.app.Application
import androidx.room.Room
import com.xxh.ringtone.world.data.AppDatabase
import com.xxh.ringtone.world.data.daos.SongDao
import org.koin.dsl.module


val DatabaseModule = module {

    single { createAppDatabase(get()) }

    single { createSongDao(get()) }

}

internal fun createAppDatabase(application: Application): AppDatabase {

    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    )
        .allowMainThreadQueries()
        .build()
}

fun createSongDao(appDatabase: AppDatabase): SongDao {
    return appDatabase.songDao
}