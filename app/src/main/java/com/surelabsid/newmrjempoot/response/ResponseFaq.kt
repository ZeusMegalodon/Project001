package com.surelabsid.newmrjempoot.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseFaq(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data_faq")
	val dataFaq: List<DataFaqItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataFaqItem(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("jawaban")
	val jawaban: String? = null,

	@field:SerializedName("pertanyaan")
	val pertanyaan: String? = null
) : Parcelable
