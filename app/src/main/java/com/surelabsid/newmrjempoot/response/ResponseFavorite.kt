package com.surelabsid.newmrjempoot.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseFavorite(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("item_fav")
	val itemFav: List<ItemFavItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ItemFavItem(

	@field:SerializedName("merchant_latitude")
	val merchantLatitude: String? = null,

	@field:SerializedName("promo_price")
	val promoPrice: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("promo_status")
	val promoStatus: String? = null,

	@field:SerializedName("merchant_id")
	val merchantId: String? = null,

	@field:SerializedName("merchant_telephone_number")
	val merchantTelephoneNumber: String? = null,

	@field:SerializedName("userid")
	val userid: String? = null,

	@field:SerializedName("merchant_category")
	val merchantCategory: String? = null,

	@field:SerializedName("item_category")
	val itemCategory: String? = null,

	@field:SerializedName("close_hour")
	val closeHour: String? = null,

	@field:SerializedName("extra")
	val extra: String? = null,

	@field:SerializedName("service_id")
	val serviceId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("merchant_address")
	val merchantAddress: String? = null,

	@field:SerializedName("open_hour")
	val openHour: String? = null,

	@field:SerializedName("added_on")
	val addedOn: String? = null,

	@field:SerializedName("disuka")
	val disuka: String? = null,

	@field:SerializedName("merchant_open_status")
	val merchantOpenStatus: String? = null,

	@field:SerializedName("merchant_phone_number")
	val merchantPhoneNumber: String? = null,

	@field:SerializedName("item_desc")
	val itemDesc: String? = null,

	@field:SerializedName("item_id")
	val itemId: String? = null,

	@field:SerializedName("item_price")
	val itemPrice: String? = null,

	@field:SerializedName("varian")
	val varian: List<String?>? = null,

	@field:SerializedName("merchant_name")
	val merchantName: String? = null,

	@field:SerializedName("item_name")
	val itemName: String? = null,

	@field:SerializedName("created_item")
	val createdItem: String? = null,

	@field:SerializedName("merchant_status")
	val merchantStatus: String? = null,

	@field:SerializedName("item_status")
	val itemStatus: String? = null,

	@field:SerializedName("merchant_desc")
	val merchantDesc: String? = null,

	@field:SerializedName("merchant_longitude")
	val merchantLongitude: String? = null,

	@field:SerializedName("merchant_country_code")
	val merchantCountryCode: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("merchant_image")
	val merchantImage: String? = null,

	@field:SerializedName("item_image")
	val itemImage: String? = null,

	@field:SerializedName("merchant_token")
	val merchantToken: String? = null
) : Parcelable
