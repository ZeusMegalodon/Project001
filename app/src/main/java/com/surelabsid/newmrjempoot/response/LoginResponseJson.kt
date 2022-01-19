package com.surelabsid.newmrjempoot.response

import com.google.gson.annotations.SerializedName
import com.surelabsid.newmrjempoot.model.UserModel

data class LoginResponseJson(
    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("data")
    var data: List<UserModel>? = null
)

