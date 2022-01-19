package com.surelabsid.newmrjempoot.response

import com.google.gson.annotations.SerializedName

data class ResponseWallet(

    @field:SerializedName("code")
    var code: Int? = null,

    @field:SerializedName("data")
    var data: List<WalletDataItem?>? = null,

    @field:SerializedName("balance")
    var balance: String? = null,

    @field:SerializedName("message")
    var message: String? = null

)

data class WalletDataItem(
   @field:SerializedName("date")
     var date: String? = null,

   @field:SerializedName("id")
     val id: String? = null,

   @field:SerializedName("id_user")
     val idUser: String? = null,

   @field:SerializedName("type")
     val type: String? = null,

   @field:SerializedName("wallet_amount")
     val walletAmount: String? = null,

   @field:SerializedName("status")
     val status: String? = null,
)