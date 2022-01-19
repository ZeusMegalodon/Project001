package com.surelabsid.newmrjempoot.response.routemodel

import com.google.gson.annotations.SerializedName
import com.surelabsid.newmrjempoot.response.routemodel.Bounds
import com.surelabsid.newmrjempoot.response.routemodel.LegsItem
import com.surelabsid.newmrjempoot.response.routemodel.OverviewPolyline

data class RoutesItem(

    @field:SerializedName("summary")
    val summary: String? = null,

    @field:SerializedName("copyrights")
    val copyrights: String? = null,

    @field:SerializedName("legs")
    val legs: List<LegsItem?>? = null,

    @field:SerializedName("warnings")
    val warnings: List<Any?>? = null,

    @field:SerializedName("bounds")
    val bounds: Bounds? = null,

    @field:SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline? = null,

    @field:SerializedName("waypoint_order")
    val waypointOrder: List<Any?>? = null
)