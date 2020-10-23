package com.xxh.ringtone.world.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xxh.ringtone.world.data.daos.SongDao
import com.xxh.ringtone.world.data.model.Song

@Database(entities = [Song::class],version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val songDao: SongDao

    companion object {
        const val DB_NAME = "ringtoneWorld.db"
    }

}