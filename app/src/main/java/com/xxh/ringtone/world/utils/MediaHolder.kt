package com.xxh.ringtone.world.utils

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class MediaHolder private constructor(){

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    fun start(path: String,next: DoAction){
        next.doBefore()

        mediaPlayer.reset()
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener{
            mediaPlayer.start()
            next.doAfter()
        }
    }

    fun pause(){
        mediaPlayer.pause()
    }

    fun release(){
        mediaPlayer.release()
    }

    fun seek(msec: Int){
        mediaPlayer.seekTo(msec)
    }

    fun reset(){
        mediaPlayer.reset()
    }

    fun duration(): Int{
        return mediaPlayer.duration
    }

    fun currentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    fun haveLoaded(): Boolean{
        return mediaPlayer.duration > 0
    }


    fun seekAndPlay(msec: Int = 0){
        mediaPlayer.start()
        mediaPlayer.seekTo(msec)
    }

    fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    interface DoAction{
        fun doAfter()
        fun doBefore()
    }

    companion object{
        private var instance: MediaHolder? =null

        @Synchronized
        fun get(): MediaHolder{
            if (null == instance){
                instance = MediaHolder()
            }
            return instance!!
        }
    }
}