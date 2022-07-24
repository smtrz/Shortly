package com.tahir.shortlyapp.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Links(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, @ColumnInfo(name = "orignal_link") val orignalLink: String?,
    @ColumnInfo(name = "short_link") val shortLink: String?
) : Parcelable