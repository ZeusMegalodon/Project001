package com.surelabsid.newmrjempoot.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class CheckStatusTransResponse(
    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("status")
    val status: Boolean = false,

    @field:SerializedName("data")
    val data: List<StatusTransModel> = ArrayList<StatusTransModel>(),

    @field:SerializedName("list_driver")

    val listDriver: List<DriverModel> = ArrayList()
)

data class StatusTransModel(
    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("description")
    val description: String? = null
)
