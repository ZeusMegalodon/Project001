package com.surelabsid.newmrjempoot.network.verihubs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surelabsid.newmrjempoot.response.ResponseRequestOtp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object VerihubsApi {
    const val BASE_URL = "https://verihubs.com/"

    private fun getOkHttp(appId: String, apiKey: String): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("App-ID", appId)
                    .addHeader("API-Key", apiKey)
                    .method(original.method(), original.body())

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    private fun getRetrofit(appId: String, apiKey: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp(appId, apiKey))
            .build()
    }

    fun getService(appId: String, apiKey: String): VerihubsService {
        return getRetrofit(appId, apiKey).create(VerihubsService::class.java)
    }
}

interface VerihubsService {
    @GET("api/v1/otp/send")
    suspend fun setOtp(@Query("msisdn") msisdn: String): ResponseRequestOtp

    @GET("api/v1/otp/verify")
    suspend fun verifyOtp(
        @Query("msisdn") msisdn: String,
        @Query("otp") otp: String
    ): ResponseRequestOtp
}

class VerihubsViewModel : ViewModel() {

    private val _send= MutableLiveData<ResponseRequestOtp>()
    val send: LiveData<ResponseRequestOtp> get() = _send

    private val _verify = MutableLiveData<ResponseRequestOtp>()
    val verify: LiveData<ResponseRequestOtp> get() = _verify

    private val _err = MutableLiveData<Throwable>()
    val err: LiveData<Throwable> get() = _err


    fun requestOtp(nomorTelepon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val api = VerihubsApi.getService(
                    appId = "46c72144-49a7-48d3-9545-5174d2317b4b",
                    apiKey = "WmuwJ8wlULOuWgXXwOTbVxPWIKOGZaBA"
                ).setOtp(nomorTelepon)
                _send.postValue(api)
            } catch (e: Throwable) {
                e.printStackTrace()
                _err.postValue(e)
            }
        }
    }

    fun verifyOtp(nomorTelepon: String, kode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val api = VerihubsApi.getService(
                    appId = "46c72144-49a7-48d3-9545-5174d2317b4b",
                    apiKey = "WmuwJ8wlULOuWgXXwOTbVxPWIKOGZaBA"
                ).verifyOtp(nomorTelepon, kode)
                _verify.postValue(api)
            } catch (e: Throwable) {
                e.printStackTrace()
                _err.postValue(e)
            }
        }
    }

}