package com.surelabsid.newmrjempoot.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseHistory(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("history")
	val history: List<HistoryItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class HistoryItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("note")
	val note: String? = null,

	@field:SerializedName("driver_id")
	val driverId: String? = null,

	@field:SerializedName("distance")
	val distance: Int? = null,

	@field:SerializedName("end_longitude")
	val endLongitude: String? = null,

	@field:SerializedName("service_order")
	val serviceOrder: String? = null,

	@field:SerializedName("pickup_address")
	val pickupAddress: String? = null,

	@field:SerializedName("order_time")
	val orderTime: String? = null,

	@field:SerializedName("end_latitude")
	val endLatitude: String? = null,

	@field:SerializedName("number")
	val number: String? = null,

	@field:SerializedName("wallet_payment")
	val walletPayment: String? = null,

	@field:SerializedName("trip")
	val trip: String? = null,

	@field:SerializedName("estimate_time")
	val estimateTime: String? = null,

	@field:SerializedName("rate")
	val rate: String? = null,

	@field:SerializedName("start_latitude")
	val startLatitude: String? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("transaction_id")
	val transactionId: String? = null,

	@field:SerializedName("destination_address")
	val destinationAddress: String? = null,

	@field:SerializedName("finish_time")
	val finishTime: String? = null,

	@field:SerializedName("start_longitude")
	val startLongitude: String? = null,

	@field:SerializedName("final_cost")
	val finalCost: String? = null,

	@field:SerializedName("driver")
	val driver: Driver? = null,

	@field:SerializedName("customer_id")
	val customerId: String? = null,

	@field:SerializedName("promo_discount")
	val promoDiscount: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable

@Parcelize
data class Driver(

	@field:SerializedName("driver_address")
	val driverAddress: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("countrycode")
	val countrycode: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("driver_birthplace")
	val driverBirthplace: String? = null,

	@field:SerializedName("reg_id")
	val regId: String? = null,

	@field:SerializedName("vehicle")
	val vehicle: String? = null,

	@field:SerializedName("driver_name")
	val driverName: String? = null,

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("update_at")
	val updateAt: String? = null,

	@field:SerializedName("user_nationid")
	val userNationid: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("job")
	val job: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
