package com.xxh.ringtone.world.data.repository

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Environment
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.xxh.ringtone.world.data.daos.SongDao
import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.utils.ReadJsonFile
import com.xxh.ringtone.world.utils.SharedPreferencesUtils
import com.xxh.ringtone.world.utils.Utils
import java.io.File


@Database(entities = [Song::class], version = 1, exportSchema = false)
abstract class RingtoneRoomDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {
        const val databaseName = "/storage/emulated/0/com.xxh.ringtone.world/ringtoneWorld.db"
        val databaseHolderName =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + "com.xxh.ringtone.world/"

        @Volatile
        private var INSTANCE: RingtoneRoomDatabase? = null

            fun getDatabase(
            context: Context,
        ): RingtoneRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RingtoneRoomDatabase::class.java,
                    databaseName
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(RingtoneDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class RingtoneDatabaseCallback(val context: Context) :
            RoomDatabase.Callback() {

            fun isFirstRuntime(context: Context): Boolean{
                return SharedPreferencesUtils.getParam(context,"first",true) as Boolean
            }

            fun setFirstRuntime(context: Context){
                SharedPreferencesUtils.setParam(context,"first",false)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                if (isFirstRuntime(context)){
                    PopulateDbAsync(INSTANCE!!).execute(context)
                    setFirstRuntime(context)
                }
            }
        }


        /**
         * Populate the database in the background.
         */
        private class PopulateDbAsync(db: RingtoneRoomDatabase) :
            AsyncTask<Context, Void?, Void?>() {
            private val mDao: SongDao = db.songDao()

            override fun doInBackground(vararg params: Context): Void? {
                val context = params[0]
                val songsArray = ReadJsonFile.getRingtoneList(context)

                songsArray.forEach {
                    mDao.insert(it)
                }
                return null
            }


        }
    }


}