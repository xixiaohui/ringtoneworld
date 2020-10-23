package com.android.musicplayer.domain.usecase


import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.data.repository.PlaylistRepository

class DeleteSongUseCase(private val playlistRepository: PlaylistRepository) {


    fun deleteSongItem(song: Song) {
        playlistRepository.delete(song)
    }
}