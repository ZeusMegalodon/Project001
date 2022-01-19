package com.surelabsid.newmrjempoot.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class PrivacyResponseJson(
    @field:SerializedName("message") @Expose
     var message: String? = null,

    @field:SerializedName("data")
    @Expose
     val data: List<SettingsModel?>? = null

)

data class SettingsModel(
    @field:SerializedName("app_privacy_policy")
    var privacy: String? = null,

    @field:SerializedName("app_syarat")
    var syarat: String? = null
)
