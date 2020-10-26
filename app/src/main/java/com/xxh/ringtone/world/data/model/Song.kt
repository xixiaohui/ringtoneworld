package com.xxh.ringtone.world.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Parcelize
@Entity
data class Song(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String?,
    var url: String,
    var artistName: String?,
    var duration: String?,
    var download: Int,
    var tags: String,
    var type: Int = 0,
): Parcelable