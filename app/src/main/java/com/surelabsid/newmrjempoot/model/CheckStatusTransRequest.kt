package com.surelabsid.newmrjempoot.model

import com.google.gson.annotations.SerializedName

data class CheckStatusTransRequest(
    @field:SerializedName("transaction_id")
    var idTransaksi: String? = null
)