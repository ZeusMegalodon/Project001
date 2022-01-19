package com.surelabsid.newmrjempoot.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "customer")
data class UserModel(
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "customer_fullname")
    var customer_fullname: String? = null,

    @ColumnInfo(name = "jenis_kelamin")
    var jk: String? = null,

    @ColumnInfo(name = "google_token")
    var google_token: String? = null,

    @ColumnInfo(name = "facebook_token")
    var facebook_token: String? = null,

    @ColumnInfo(name = "balance")
    val balance: String? = null,

    @ColumnInfo(name = "email_verified")
    var email_verified: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "phone_number")
    var phone_number: String? = null,

    @ColumnInfo(name = "countrycode")
    var countrycode: String? = null,

    @ColumnInfo(name = "phone")
    var phone: String? = null,

    @ColumnInfo(name = "password")
    var password: String? = null,

    @ColumnInfo(name = "dob")
    var dob: String? = null,

    @ColumnInfo(name = "customer_rating")
    var customer_rating: String? = null,

    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "token")
    var token: String? = null,

    @ColumnInfo(name = "customer_image")
    var customer_image: String? = null,
) : Parcelable