package com.surelabsid.newmrjempoot.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseLogin(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("user_data")
	val userData: UserData? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class UserData(

	@field:SerializedName("countrycode")
	val countrycode: String? = null,

	@field:SerializedName("jk")
	val jk: String? = null,

	@field:SerializedName("customer_fullname")
	val customerFullname: String? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("customer_rating")
	val customerRating: String? = null,

	@field:SerializedName("balance")
	val balance: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@ColumnInfo(name = "facebook_token")
	var facebook_tokenemail_verified: String? = null,

	@ColumnInfo(name = "google_token")
	var google_token: String? = null,

	@field:SerializedName("created_on")
	val createdOn: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("customer_image")
	val customerImage: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
