package com.xxh.ringtone.world.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.xxh.ringtone.world.data.model.Song
import com.xxh.ringtone.world.data.repository.RingtoneRepository
import com.xxh.ringtone.world.utils.SharedPreferencesUtils


class RingtoneViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RingtoneRepository = RingtoneRepository(application)

    private val allRingtones: LiveData<List<Song>>

    init {
        allRingtones = repository.getAllRingtones()
    }

    fun insert(ringtone: Song) {
        repository.insert(ringtone)
    }

    fun insertRingtoneList(ringtones: List<Song>){
        repository.insertRingtoneList(ringtones)
    }

    fun delete(ringtone: Song){
        repository.delete(ringtone)
    }

    fun getAllRingtones():LiveData<List<Song>>{
        return allRingtones
    }

    fun deleteAll(){
        repository
    }


    fun update(ringtone: Song){
        repository.update(ringtone)
    }

    fun updateByTitle(filename: String,isSelect: Boolean){
        repository.updateByTitle(filename,isSelect)
    }

    fun isFirstRuntime(): Boolean{
        return SharedPreferencesUtils.getParam(getApplication(),"first",false) as Boolean
    }

    fun setFirstRuntime(){
        SharedPreferencesUtils.setParam(getApplication(),"first",true)
    }



}