package com.surelabsid.newmrjempoot.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class RideCarResponseJson(
    @field:SerializedName("message")
    var message: String? = null,


    @field:SerializedName("data")
    var data: List<TransModel> = ArrayList<TransModel>()
)

data class TransModel(
    @field:SerializedName("id")
    var id: String? = null,


    @field:SerializedName("customer_id")
    var idPelanggan: String? = null,


    @field:SerializedName("driver_id")
    var idDriver: String? = null,


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
    var price: Long = 0,


    @field:SerializedName("order_time")
    var waktuOrder: String? = null,


    @field:SerializedName("finish_time")
    var waktuSelesai: String? = null,


    @field:SerializedName("pickup_address")
    var alamatAsal: String? = null,


    @field:SerializedName("destination_address")
    var alamatTujuan: String? = null,


    @field:SerializedName("promo_code")
    var kodePromo: String? = null,


    @field:SerializedName("promo_discount")
    var kreditPromo: String? = null,


    @field:SerializedName("wallet_payment")
    var pakaiWallet: Boolean = false,


    @field:SerializedName("rate")
    var rate: String? = null,


    @field:SerializedName("status")
    var status: Int = 0,


    @field:SerializedName("estimate_time")
    var estimasi: String? = null,


    @field:SerializedName("sender_name")
    var namaPengirim: String? = null,


    @field:SerializedName("sender_phone")
    var teleponPengirim: String? = null,


    @field:SerializedName("receiver_name")
    var namaPenerima: String? = null,


    @field:SerializedName("receiver_phone")
    var teleponPenerima: String? = null,


    @field:SerializedName("goods_item")
    var namaBarang: String? = null,


    @field:SerializedName("final_cost")
    var final_cost: String? = null,


    @field:SerializedName("total_price")
    var total_price: String? = null,


    @field:SerializedName("merchant_name")
    var merchant_name: String? = null,


    @field:SerializedName("merchant_token")
    var merchant_token: String? = null,


    @field:SerializedName("merchant_transaction_id")
    var idtransmerchant: String? = null,


    @field:SerializedName("metode_bayar")
    var metodeBayar: String? = null,


    @field:SerializedName("trip")
    var trip: String? = null,
)
