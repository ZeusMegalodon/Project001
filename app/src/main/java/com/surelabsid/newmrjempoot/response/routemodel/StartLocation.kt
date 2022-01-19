package com.surelabsid.newmrjempoot.response.routemodel

import com.google.gson.annotations.SerializedName

data class StartLocation(

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)