package com.surelabsid.newmrjempoot.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "item_gopek")
data class Cart(
    @PrimaryKey
    var id: Long = System.currentTimeMillis(),

    @field:SerializedName("merchant_latitude")
    @ColumnInfo(name="merchant_latitude")
    var merchantLatitude: String? = null,

    @field:SerializedName("promo_price")
    @ColumnInfo(name="promo_price")
    var promoPrice: String? = null,

    @field:SerializedName("rating")
    @ColumnInfo(name="rating")
    var rating: String? = null,

    @field:SerializedName("promo_status")
    @ColumnInfo(name="promo_status")
    var promoStatus: String? = null,

    @field:SerializedName("merchant_id")
    @ColumnInfo(name="merchant_id")
    var merchantId: String? = null,

    @field:SerializedName("merchant_telephone_number")
    @ColumnInfo(name="merchant_telephone_number")
    var merchantTelephoneNumber: String? = null,

    @field:SerializedName("merchant_category")
    @ColumnInfo(name="merchant_category")
    var merchantCategory: String? = null,

    @field:SerializedName("item_category")
    @ColumnInfo(name="item_category")
    var itemCategory: String? = null,

    @field:SerializedName("close_hour")
    @ColumnInfo(name="close_hour")
    var closeHour: String? = null,

    @field:SerializedName("extra")
    @ColumnInfo(name="extra")
    var extra: String? = null,

    @field:SerializedName("service_id")
    @ColumnInfo(name="service_id")
    var serviceId: String? = null,

    @field:SerializedName("merchant_address")
    @ColumnInfo(name="merchant_address")
    var merchantAddress: String? = null,

    @field:SerializedName("open_hour")
    @ColumnInfo(name="open_hour")
    var openHour: String? = null,

    @field:SerializedName("disuka")
    @ColumnInfo(name="disuka")
    var disuka: String? = null,

    @field:SerializedName("merchant_open_status")
    @ColumnInfo(name="merchant_open_status")
    var merchantOpenStatus: String? = null,

    @field:SerializedName("merchant_phone_number")
    @ColumnInfo(name="merchant_phone_number")
    var merchantPhoneNumber: String? = null,

    @field:SerializedName("item_desc")
    @ColumnInfo(name="item_desc")
    var itemDesc: String? = null,

    @field:SerializedName("item_id")
    @ColumnInfo(name="item_id")
    var itemId: String? = null,

    @field:SerializedName("item_price")
    @ColumnInfo(name="item_price")
    var itemPrice: String? = null,

    @ColumnInfo(name = "varian")
    var _varian: String? = null,

    @field:SerializedName("merchant_name")
    @ColumnInfo(name="merchant_name")
    var merchantName: String? = null,

    @field:SerializedName("item_name")
    @ColumnInfo(name="item_name")
    var itemName: String? = null,

    @field:SerializedName("created_item")
    @ColumnInfo(name="created_item")
    var createdItem: String? = null,

    @field:SerializedName("merchant_status")
    @ColumnInfo(name="merchant_status")
    var merchantStatus: String? = null,

    @field:SerializedName("item_status")
    @ColumnInfo(name="item_status")
    var itemStatus: String? = null,

    @field:SerializedName("merchant_desc")
    @ColumnInfo(name="merchant_desc")
    var merchantDesc: String? = null,

    @field:SerializedName("merchant_longitude")
    @ColumnInfo(name="merchant_longitude")
    var merchantLongitude: String? = null,

    @field:SerializedName("merchant_country_code")
    @ColumnInfo(name="merchant_country_code")
    var merchantCountryCode: String? = null,

    @field:SerializedName("jenis")
    @ColumnInfo(name="jenis")
    var jenis: String? = null,

    @field:SerializedName("merchant_image")
    @ColumnInfo(name="merchant_image")
    var merchantImage: String? = null,

    @field:SerializedName("item_image")
    @ColumnInfo(name="item_image")
    var itemImage: String? = null,

    @field:SerializedName("merchant_token")
    @ColumnInfo(name="merchant_token")
    var merchantToken: String? = null
)
