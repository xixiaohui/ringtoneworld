package com.android.musicplayer.domain.usecase

import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.data.repository.PlaylistRepository


class GetSongsUseCase(private val playlistRepository: PlaylistRepository) {
    fun getSongs(): List<Song>? {
        return playlistRepository.getSongs()
    }
}