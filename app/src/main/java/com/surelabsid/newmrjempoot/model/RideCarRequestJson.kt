package com.surelabsid.newmrjempoot.model

import com.google.gson.annotations.SerializedName

data class RideCarRequestJson(
    @field:SerializedName("customer_id")
    var idPelanggan: String? = null,


    @field:SerializedName("service_order")
    var orderFitur: String? = null,


    @field:SerializedName("start_latitude")
    var startLatitude: Double? = 0.0,


    @field:SerializedName("start_longitude")
    var startLongitude: Double? = 0.0,


    @field:SerializedName("end_latitude")
    var endLatitude: Double? = 0.0,


    @field:SerializedName("end_longitude")
    var endLongitude: Double? = 0.0,


    @field:SerializedName("distance")
    var distance: Double? = 0.0,


    @field:SerializedName("price")
    var price: Long = 0L,


    @field:SerializedName("pickup_address")
    var alamatAsal: String? = null,


    @field:SerializedName("destination_address")
    var alamatTujuan: String? = null,


    @field:SerializedName("wallet_payment")
    var pakaiWallet: Int = 0,


    @field:SerializedName("promo_discount")
    var kreditpromo: String? = null,


    @field:SerializedName("estimasi")
    var estimasi: String? = null,


    @field:SerializedName("trip")
    var trip: String? = null,

    )