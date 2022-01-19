package com.surelabsid.newmrjempoot.network

import com.surelabsid.newmrjempoot.model.*
import com.surelabsid.newmrjempoot.response.*
import com.surelabsid.newmrjempoot.response.routemodel.ResultRoute
import com.surelabsid.newmrjempoot.wallet.WalletRequestJson
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("api/Customer/doRegister")
    suspend fun doRegister(@Body userModel: UserModel): GeneralResponse


    @POST("api/customerapi/home")
    suspend fun getHomeData(@Body jsonRequest: JsonRequest): ResponseHome

    @POST("api/Customer/doLogin")
    suspend fun login(@Body userModel: UserModel): ResponseLogin


    @GET("json")
    suspend fun getRoute(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") api: String
    ): ResultRoute

    @POST("api/customerapi/list_ride")
    suspend fun getNearRide(@Body param: NearbyDriverRequest?): ResponseNearbyDriver

    @GET("api/Gopek/searchGopek")
    suspend fun searchGopek(@Query("q") q: String?) : ResponseGopek

    @POST("api/Wallet/getWalletHistory")
    suspend fun wallet(@Body param: WalletRequestJson?): ResponseWallet


    @POST("api/customerapi/privacy")
    suspend fun privacy(): PrivacyResponseJson


    @POST("api/Wallet/reqTopup")
    suspend fun requestSaldo(@Body walletRequest: WalletRequestJson): GeneralResponse


    @POST("api/customerapi/request_transaksi")
    suspend fun requestTransaksi(@Body param: RideCarRequestJson?): RideCarResponseJson


    @POST("api/customerapi/check_status_transaksi")
    suspend fun checkStatusTransaksi(@Body param: CheckStatusTransRequest?): CheckStatusTransResponse


    @POST("api/customerapi/privacy")
    suspend fun getSyarat(): PrivacyResponseJson

    @GET("api/Appsettings/getFaq")
    suspend fun getFaq(): ResponseFaq


    @POST("api/User/checkStatusMember")
    suspend fun checkMember(@Body ktpReq: KtpReq): ResponseCheckStatus


    @POST("api/User/getKtpUser")
    suspend fun sendDataKtp(@Body ktpReq: KtpReq?): GeneralResponse


    @GET("api/History/getHistory")
    suspend fun getHistory(
        @Query("status") status: Int?,
        @Query("service") service: Int?,
        @Query("userid") userid: String?
    ): ResponseHistory

    @GET("api/Gopek/getAllGopek")
    suspend fun getAllGopek(
        @Query("lat") lat: String?,
        @Query("lon") lon: String?
    ): ResponseGopek


    @GET("api/Gopek/getGopekItem")
    suspend fun getGopekItem(
        @Query("jenis") jenis: String? = "makanan",
        @Query("lat") lat: String?,
        @Query("lon") lon: String?
    ): ResponseGopek


    @GET("api/User/getFavoriteList")
    suspend fun getFavoriteList(@Query("userid") userid: String?): ResponseGopek


    @POST("api/User/addWishList")
    suspend fun addFavorite(
        @Body favModel: FavModel
    ): GeneralResponse
}
