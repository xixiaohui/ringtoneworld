package com.xxh.ringtone.world.data.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.xxh.ringtone.world.data.daos.SongDao
import com.xxh.ringtone.world.data.model.Song

class RingtoneRepository(application: Application) {

    var mAllRingtones: LiveData<List<Song>>
    private lateinit var ringtoneDao: SongDao

    init {
        val db = RingtoneRoomDatabase.getDatabase(application)
        ringtoneDao = db.songDao()
        mAllRingtones = ringtoneDao.getAll()
    }

    fun getAllRingtones():LiveData<List<Song>>{
        return mAllRingtones
    }

    fun insert(ringtone: Song) {
        insertAsyncTask(ringtoneDao).execute(ringtone)
    }

    fun insertRingtoneList(ringtones: List<Song>) {
        insertRingtoneListAsyncTask(ringtoneDao).execute(ringtones)
    }


    fun getRingtoneByTitle(title: String): Song {
        val ringtone = ringtoneDao.loadOneBySongTitle(title)
        return ringtone!!
    }

    fun delete(ringtone: Song){
        deleteAsyncTask(ringtoneDao).execute(ringtone)
    }

    fun update(ringtone: Song){
        updateAsyncTask(ringtoneDao).execute(ringtone)
    }

    fun updateByTitle(title: String,isSelect: Boolean) {
        updateByFilenameAsyncTask(ringtoneDao,isSelect).execute(title)
    }


    private class updateByFilenameAsyncTask(val dao: SongDao,val isSelect: Boolean) : AsyncTask<String, Void, Void>() {
        private var mAsyncTaskDao: SongDao? = null

        init {
            mAsyncTaskDao = dao
        }

        override fun doInBackground(vararg params: String): Void? {

            val title = params[0]
            val ringtone = mAsyncTaskDao!!.loadOneBySongTitle(title)
            mAsyncTaskDao!!.update(ringtone!!)
            return null
        }
    }

    private class updateAsyncTask(val dao: SongDao) : AsyncTask<Song, Void, Void>() {
        private var mAsyncTaskDao: SongDao? = null

        init {
            mAsyncTaskDao = dao
        }

        override fun doInBackground(vararg params: Song): Void? {
            mAsyncTaskDao!!.update(params[0])
            return null
        }
    }


    private class insertRingtoneListAsyncTask(val dao: SongDao) : AsyncTask<List<Song>, Void, Void>() {
        private var mAsyncTaskDao: SongDao? = null

        init {
            mAsyncTaskDao = dao
        }

        override fun doInBackground(vararg params: List<Song>): Void? {

            val ringtones = params[0]

            ringtones.forEach{
                mAsyncTaskDao!!.insert(it)
            }

            return null
        }
    }


    private class insertAsyncTask(val dao: SongDao) : AsyncTask<Song, Void, Void>() {
        private var mAsyncTaskDao: SongDao? = null

        init {
            mAsyncTaskDao = dao
        }

        override fun doInBackground(vararg params: Song): Void? {
            mAsyncTaskDao!!.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask(val dao: SongDao) : AsyncTask<Song, Void, Void>() {
        private var mAsyncTaskDao: SongDao? = null

        init {
            mAsyncTaskDao = dao
        }

        override fun doInBackground(vararg params: Song): Void? {
            mAsyncTaskDao!!.delete(params[0])
            return null
        }
    }

}