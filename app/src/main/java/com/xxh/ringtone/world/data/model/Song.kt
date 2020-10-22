package com.xxh.ringtone.world.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Suppress("DIFFERENT_NAMES_FOR_THE_SAME_PARAMETER_IN_SUPERTYPES")
@Parcelize
@Entity
data class Song(

    @PrimaryKey
    var id: Int,
    var title: String?,
    var url: String,
    var artistName: String?,
    var duration: String?,
    var tags: ArrayList<String>,
    var download: Int,
    var type: Int = 0,

): Parcelable