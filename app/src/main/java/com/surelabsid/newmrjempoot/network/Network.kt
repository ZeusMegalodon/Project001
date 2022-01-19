package com.surelabsid.newmrjempoot.network

import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    //    const val BASE_URL = "https://server4111.com/jempoot/"
    const val BASE_URL = "http://192.168.1.103/mrjempoot/"
    private val BASE_URL_ROUTE = "https://maps.googleapis.com/maps/api/directions/"
    const val BASE_URL_SURELABS = "https://surelabsid.com/mr-jempoot/"

    fun getOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    fun okhttpWithAuth(phone: String?, password: String?): OkHttpClient {
        val credentials = "$phone:$password"
        val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .header("Authorization", basic)
                    .header("Accept", "application/json")
                    .method(chain.request().method(), chain.request().body()).build()

                chain.proceed(request)
            }
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
//            .baseUrl(BASE_URL + "api/")
            .baseUrl(BASE_URL_SURELABS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
    }

    private fun getRetrofitRoute(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_ROUTE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
    }

    fun getRetrofitSurelabs(phone: String?, password: String?): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_SURELABS)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpWithAuth(phone, password))
            .build().create(ApiService::class.java)
    }

    fun getService(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }

    fun getServiceRoute(): ApiService {
        return getRetrofitRoute().create(ApiService::class.java)
    }
}