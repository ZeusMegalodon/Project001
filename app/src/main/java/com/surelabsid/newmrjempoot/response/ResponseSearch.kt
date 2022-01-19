package com.surelabsid.newmrjempoot.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseSearch(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("item_gopek")
	val itemGopek: List<ItemGopekItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class ItemGopekItem(

	@field:SerializedName("item_desc")
	val itemDesc: String? = null,

	@field:SerializedName("item_id")
	val itemId: String? = null,

	@field:SerializedName("item_price")
	val itemPrice: String? = null,

	@field:SerializedName("promo_price")
	val promoPrice: String? = null,

	@field:SerializedName("varian")
	val varian: List<String?>? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("promo_status")
	val promoStatus: String? = null,

	@field:SerializedName("item_name")
	val itemName: String? = null,

	@field:SerializedName("created_item")
	val createdItem: String? = null,

	@field:SerializedName("merchant_id")
	val merchantId: String? = null,

	@field:SerializedName("item_status")
	val itemStatus: String? = null,

	@field:SerializedName("item_category")
	val itemCategory: String? = null,

	@field:SerializedName("extra")
	val extra: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("item_image")
	val itemImage: String? = null,

	@field:SerializedName("disuka")
	val disuka: String? = null
) : Parcelable
