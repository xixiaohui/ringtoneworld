package com.xxh.ringtone.world.data.repository

import com.xxh.ringtone.world.data.model.Song

interface PlaylistRepository {

    fun saveSongData(song: Song):Long

    fun getSongs(): List<Song>?

    fun delete(song: Song)
}