package com.android.musicplayer.domain.usecase

import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.data.repository.PlaylistRepository


class SaveSongDataUseCase(private val playlistRepository: PlaylistRepository) {

    fun saveSongItem(song: Song) {
        playlistRepository.saveSongData(song)
    }
}