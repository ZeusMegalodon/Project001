package com.surelabsid.newmrjempoot.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class DriverRequest(

    @field:SerializedName("type")
    var type: Int = 0,


    @field:SerializedName("transaction_id")
    var idTransaksi: String? = null,


    @field:SerializedName("customer_id")
    var idPelanggan: String? = null,


    @field:SerializedName("reg_id_pelanggan")
    var regIdPelanggan: String? = null,


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


    @field:SerializedName("jarak")
    var jarak: Double? = 0.0,


    @field:SerializedName("price")
    var price: Long? = 0,


    @field:SerializedName("order_time")
    var waktuOrder: String? = null,


    @field:SerializedName("finish_time")
    var waktuSelesai: String? = null,


    @field:SerializedName("pickup_address")
    var alamatAsal: String? = null,


    @field:SerializedName("destination_address")
    var alamatTujuan: String? = null,


    @field:SerializedName("rate")
    var rate: String? = null,


    @field:SerializedName("promo_code")
    var kodePromo: String? = null,


    @field:SerializedName("promo_discount")
    var kreditPromo: String? = null,


    @field:SerializedName("wallet_payment")
    var pakaiWallet: String? = null,


    @field:SerializedName("nama_pelanggan")
    var namaPelanggan: String? = null,


    @field:SerializedName("telepon")
    var telepon: String? = null,


    @field:SerializedName("time_accept")
    var time_accept: String? = null,


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


    @field:SerializedName("tanggal_pelayanan")
    var tanggalPelayanan: String? = null,


    @field:SerializedName("jam_pelayanan")
    var jamPelayanan: String? = null,


    @field:SerializedName("quantity")
    var quantity: Int = 0,


    @field:SerializedName("residential_type")
    var residentialType: String? = null,


    @field:SerializedName("problem")
    var problem: String? = null,


    @field:SerializedName("id_jenis")
    var idJenis: Int = 0,


    @field:SerializedName("jenis_service")
    var jenisService: String? = null,


    @field:SerializedName("ac_type")
    var acType: String? = null,


    @field:SerializedName("fare")
    var fare: Double = 0.0,


    @field:SerializedName("estimate_time")
    var estimasi: String? = null,


    @field:SerializedName("icon")
    var icon: String? = null,


    @field:SerializedName("layanan")
    var layanan: String? = null,


    @field:SerializedName("layanandesc")
    var layanandesc: String? = null,


    @field:SerializedName("distance")
    var distance: String? = null,


    @field:SerializedName("cost")
    var cost: String? = null,


    @field:SerializedName("merchant_token")
    var tokenmerchant: String? = null,


    @field:SerializedName("merchant_transaction_id")
    var idtransmerchant: String? = null,
)