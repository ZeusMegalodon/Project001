package com.surelabsid.newmrjempoot.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseHome(

	@field:SerializedName("stripe_active")
	var stripeActive: String? = null,

	@field:SerializedName("slider")
	var slider: List<SliderItem?>? = null,

	@field:SerializedName("app_contact")
	var appContact: String? = null,

	@field:SerializedName("code")
	var code: String? = null,

	@field:SerializedName("data")
	var data: List<DataItem?>? = null,

	@field:SerializedName("currency_text")
	var currencyText: String? = null,

	@field:SerializedName("stripe_publish")
	var stripePublish: String? = null,

	@field:SerializedName("paypal_key")
	var paypalKey: String? = null,

	@field:SerializedName("balance")
	var balance: String? = null,

	@field:SerializedName("currency")
	var currency: String? = null,

	@field:SerializedName("fav_meal")
	var favMeal: List<FavMealItem?>? = null,

	@field:SerializedName("paypal_mode")
	var paypalMode: String? = null,

	@field:SerializedName("app_aboutus")
	var appAboutus: String? = null,

	@field:SerializedName("allfitur")
	var allfitur: List<AllfiturItem?>? = null,

	@field:SerializedName("message")
	var message: String? = null,

	@field:SerializedName("app_email")
	var appEmail: String? = null,

	@field:SerializedName("service")
	var service: List<ServiceItem?>? = null,

	@field:SerializedName("payu")
	var payu: List<PayuItem?>? = null,

	@field:SerializedName("paypal_active")
	var paypalActive: String? = null,

	@field:SerializedName("kategorymerchanthome")
	var kategorymerchanthome: List<KategorymerchanthomeItem?>? = null,

	@field:SerializedName("app_website")
	var appWebsite: String? = null,

	@field:SerializedName("nearby_meal")
	var nearbyMeal: List<FavMealItem?>? = null,

	@field:SerializedName("app_cost")
	var appCost: String? = null
) : Parcelable

@Parcelize
data class KategorymerchanthomeItem(

	@field:SerializedName("category_name")
	var categoryName: String? = null,

	@field:SerializedName("service_id")
	var serviceId: String? = null,

	@field:SerializedName("category_status")
	var categoryStatus: String? = null,

	@field:SerializedName("category_merchant_id")
	var categoryMerchantId: String? = null,

	@field:SerializedName("category_images")
	var categoryImages: String? = null
) : Parcelable

@Parcelize
@Entity(tableName = "tb_favorite")
data class FavMealItem(

	@PrimaryKey
	var id : String = System.currentTimeMillis().toString(),

	@field:SerializedName("raw_item_price")
	@ColumnInfo(name = "raw_item_price")
	var rawItemPrice: String? = null,

	@field:SerializedName("item_desc")
	@ColumnInfo(name = "item_desc")
	var itemDesc: String? = null,

	@field:SerializedName("item_id")
	@ColumnInfo(name = "item_id")
	var itemId: String? = null,

	@field:SerializedName("item_price")
	@ColumnInfo(name = "item_price")
	var itemPrice: String? = null,

	@field:SerializedName("promo_price")
	@ColumnInfo(name = "promo_price")
	var promoPrice: String? = null,

	@field:SerializedName("varian")
	@ColumnInfo(name = "varian")
	var varian: String? = null,

	@field:SerializedName("rating")
	@ColumnInfo(name = "rating")
	var rating: String? = null,

	@field:SerializedName("promo_status")
	@ColumnInfo(name = "promo_status")
	var promoStatus: String? = null,

	@field:SerializedName("item_name")
	@ColumnInfo(name = "item_name")
	var itemName: String? = null,

	@field:SerializedName("created_item")
	@ColumnInfo(name = "created_item")
	var createdItem: String? = null,

	@field:SerializedName("merchant_id")
	@ColumnInfo(name = "merchant_id")
	var merchantId: String? = null,

	@field:SerializedName("item_status")
	@ColumnInfo(name = "item_status")
	var itemStatus: String? = null,

	@field:SerializedName("item_category")
	@ColumnInfo(name = "item_category")
	var itemCategory: String? = null,

	@field:SerializedName("off")
	@ColumnInfo(name = "off")
	var off: Int? = null,

	@field:SerializedName("raw_promo_price")
	@ColumnInfo(name = "raw_promo_price")
	var rawPromoPrice: String? = null,

	@field:SerializedName("extra")
	@ColumnInfo(name = "extra")
	var extra: String? = null,

	@field:SerializedName("item_image")
	@ColumnInfo(name = "item_image")
	var itemImage: String? = null,

	@field:SerializedName("disuka")
	@ColumnInfo(name = "disuka")
	var disuka: String? = null
) : Parcelable

@Entity(tableName = "tb_service")
@Parcelize
data class AllfiturItem(

	@field:SerializedName("cost_desc")
	@ColumnInfo(name = "cost_desc")
	var costDesc: String? = null,

	@field:SerializedName("cost")
	@ColumnInfo(name = "cost")
	var cost: String? = null,

	@field:SerializedName("icon")
	@ColumnInfo(name = "icon")
	var icon: String? = null,

	@field:SerializedName("description")
	@ColumnInfo(name = "description")
	var description: String? = null,

	@field:SerializedName("active")
	@ColumnInfo(name = "active")
	var active: String? = null,

	@field:SerializedName("commision")
	@ColumnInfo(name = "commision")
	var commision: String? = null,

	@field:SerializedName("minimum_cost")
	@ColumnInfo(name = "minimum_cost")
	var minimumCost: String? = null,

	@field:SerializedName("minimum_distance")
	@ColumnInfo(name = "minimum_distance")
	var minimumDistance: String? = null,

	@field:SerializedName("minimum_wallet")
	@ColumnInfo(name = "minimum_wallet")
	var minimumWallet: String? = null,

	@field:SerializedName("home")
	@ColumnInfo(name = "home")
	var home: String? = null,

	@field:SerializedName("service")
	@ColumnInfo(name = "service")
	var service: String? = null,

	@field:SerializedName("service_id")
	@ColumnInfo(name = "service_id")
	@PrimaryKey
	var serviceId: String = "",

	@field:SerializedName("driver_job")
	@ColumnInfo(name = "driver_job")
	var driverJob: String? = null,

	@field:SerializedName("maks_distance")
	@ColumnInfo(name = "maks_distance")
	var maksDistance: String? = null
) : Parcelable

@Parcelize
data class PayuItem(

	@field:SerializedName("payu_id")
	var payuId: String? = null,

	@field:SerializedName("payu_debug")
	var payuDebug: String? = null,

	@field:SerializedName("payu_salt")
	var payuSalt: String? = null,

	@field:SerializedName("payu_key")
	var payuKey: String? = null,

	@field:SerializedName("active")
	var active: String? = null,

	@field:SerializedName("id")
	var id: String? = null
) : Parcelable

@Parcelize
data class ServiceItem(

	@field:SerializedName("cost_desc")
	var costDesc: String? = null,

	@field:SerializedName("cost")
	var cost: String? = null,

	@field:SerializedName("icon")
	var icon: String? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("active")
	var active: String? = null,

	@field:SerializedName("commision")
	var commision: String? = null,

	@field:SerializedName("minimum_cost")
	var minimumCost: String? = null,

	@field:SerializedName("minimum_distance")
	var minimumDistance: String? = null,

	@field:SerializedName("minimum_wallet")
	var minimumWallet: String? = null,

	@field:SerializedName("home")
	var home: String? = null,

	@field:SerializedName("service")
	var service: String? = null,

	@field:SerializedName("service_id")
	var serviceId: String? = null,

	@field:SerializedName("driver_job")
	var driverJob: String? = null,

	@field:SerializedName("maks_distance")
	var maksDistance: String? = null
) : Parcelable

@Parcelize
data class SliderItem(

	@field:SerializedName("promotion_type")
	var promotionType: String? = null,

	@field:SerializedName("cost_desc")
	var costDesc: String? = null,

	@field:SerializedName("cost")
	var cost: String? = null,

	@field:SerializedName("promotion_link")
	var promotionLink: String? = null,

	@field:SerializedName("icon")
	var icon: String? = null,

	@field:SerializedName("photo")
	var photo: String? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("active")
	var active: String? = null,

	@field:SerializedName("commision")
	var commision: String? = null,

	@field:SerializedName("minimum_cost")
	var minimumCost: String? = null,

	@field:SerializedName("is_show")
	var isShow: String? = null,

	@field:SerializedName("minimum_distance")
	var minimumDistance: String? = null,

	@field:SerializedName("minimum_wallet")
	var minimumWallet: String? = null,

	@field:SerializedName("home")
	var home: String? = null,

	@field:SerializedName("created_on")
	var createdOn: String? = null,

	@field:SerializedName("service")
	var service: String? = null,

	@field:SerializedName("service_id")
	var serviceId: String? = null,

	@field:SerializedName("driver_job")
	var driverJob: String? = null,

	@field:SerializedName("action")
	var action: String? = null,

	@field:SerializedName("exp_date")
	var expDate: String? = null,

	@field:SerializedName("id")
	var id: String? = null,

	@field:SerializedName("maks_distance")
	var maksDistance: String? = null,

	@field:SerializedName("promotion_service")
	var promotionService: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("jk")
	var jk: String? = null,

	@field:SerializedName("is_verify_email")
	var isVerifyEmail: String? = null,

	@field:SerializedName("countrycode")
	var countrycode: String? = null,

	@field:SerializedName("customer_fullname")
	var customerFullname: String? = null,

	@field:SerializedName("token")
	var token: String? = null,

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("customer_rating")
	var customerRating: String? = null,

	@field:SerializedName("balance")
	var balance: String? = null,

	@field:SerializedName("phone")
	var phone: String? = null,

	@field:SerializedName("created_on")
	var createdOn: String? = null,

	@field:SerializedName("dob")
	var dob: String? = null,

	@field:SerializedName("phone_number")
	var phoneNumber: String? = null,

	@field:SerializedName("id")
	var id: String? = null,

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("customer_image")
	var customerImage: String? = null,

	@field:SerializedName("is_verify_phone")
	var isVerifyPhone: String? = null,

	@field:SerializedName("status")
	var status: String? = null
) : Parcelable
