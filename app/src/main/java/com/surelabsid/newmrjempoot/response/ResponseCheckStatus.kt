package com.surelabsid.newmrjempoot.response

import com.google.gson.annotations.SerializedName

data class ResponseCheckStatus(
    @field:SerializedName("code")
    var code: Int = 0,

    @field:SerializedName("message")
    var message: String? = null,

    @field:SerializedName("data_member")
    var dataMember: DataMember? = null
)


data class DataMember(
    @field:SerializedName("jk")
    var jk: String? = null,

    @field:SerializedName("is_verify_email")
    var isVerifyEmail: String? = null,

    @field:SerializedName("no_ktp")
    var noKtp: String? = null,

    @field:SerializedName("ktp")
    var ktp: String? = null,

    @field:SerializedName("is_verify")
    var isVerify: String? = null,

    @field:SerializedName("countrycode")
    var countrycode: String? = null,

    @field:SerializedName("id_user")
    var idUser: String? = null,

    @field:SerializedName("customer_fullname")
    var customerFullname: String? = null,

    @field:SerializedName("token")
    var token: String? = null,

    @field:SerializedName("password")
    var password: String? = null,

    @field:SerializedName("customer_rating")
    var customerRating: String? = null,

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

    @field:SerializedName("jenis_kelamin")
    var jenisKelamin: String? = null,

    @field:SerializedName("added_on")
    var addedOn: String? = null,

    @field:SerializedName("tanggal_lahir")
    var tanggalLahir: String? = null,

    @field:SerializedName("email")
    var email: String? = null,

    @field:SerializedName("customer_image")
    var customerImage: String? = null,

    @field:SerializedName("is_verify_phone")
    var isVerifyPhone: String? = null,

    @field:SerializedName("expired_on")
    var expiredOn: String? = null,

    @field:SerializedName("status")
    var status: String? = null,

    @field:SerializedName("member_type")
    var memberType: String? = null,

    )