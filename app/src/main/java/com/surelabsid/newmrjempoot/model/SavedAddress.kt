package com.surelabsid.newmrjempoot.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_address")
data class SavedAddress(
    @PrimaryKey
    var id: String = System.currentTimeMillis().toString(),

    @ColumnInfo(name = "label")
    var label: String? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "extra")
    var extra: String? = null,

    @ColumnInfo(name = "lat")
    var lat: String? = null,

    @ColumnInfo(name = "lon")
    var lon: String? = null

) : Parcelable