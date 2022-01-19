package com.surelabsid.newmrjempoot.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class ResponseNearbyDriver(
    @field:SerializedName("data")
    var data: List<DriverModel?>? = null

)

data class DriverModel(
    @field:SerializedName("latitude")
    var latitude: Double? = null,


    @field:SerializedName("longitude")
    var longitude: Double? = null,


    @field:SerializedName("update_at")
    var updateAt: String? = null,


    @field:SerializedName("phone_number")
    var noTelepon: String? = null,


    @field:SerializedName("photo")
    var photo: String? = null,


    @field:SerializedName("reg_id")
    var regId: String? = null,


    @field:SerializedName("driver_job")
    var driverJob: String? = null,


    @field:SerializedName("distance")
    var distance: String? = null,


    @field:SerializedName("brand")
    var brand: String? = null,


    @field:SerializedName("vehicle_registration_number")
    var vehicle_registration_number: String? = null,


    @field:SerializedName("color")
    var color: String? = null,


    @field:SerializedName("type")
    var type: String? = null,


    @field:SerializedName("bearing")
    var bearing: String? = null,

    @field:SerializedName("id")
    var id: String? = null

)
