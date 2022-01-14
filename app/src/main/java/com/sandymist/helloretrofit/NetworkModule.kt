package com.sandymist.helloretrofit

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private lateinit var webservice: Webservice

    fun initRetrofit() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        webservice = retrofit.create(Webservice::class.java)
    }

    suspend fun getData() {
        val data = webservice.getData()
        Log.d(TAG, "Data: $data")
    }

    private const val TAG = "NetworkModule"
    private const val BASE_URL = "http://10.0.2.2:8000"
}