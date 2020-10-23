package com.xxh.ringtone.world.data.repository

import com.xxh.ringtone.world.data.AppDatabase
import com.xxh.ringtone.world.data.model.Song

class PlaylistRepositoryImp(private val appDatabase: AppDatabase) : PlaylistRepository {


    override fun saveSongData(song: Song): Long {
        return appDatabase.songDao.insert(song)
    }

    override fun getSongs(): List<Song>? {
        return appDatabase.songDao.loadAll()
    }

    override fun delete(song: Song) {
        appDatabase.songDao.delete(song)
    }
}