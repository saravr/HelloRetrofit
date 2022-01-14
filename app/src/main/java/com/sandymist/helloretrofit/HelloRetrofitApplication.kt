package com.sandymist.helloretrofit

import android.app.Application
import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HelloRetrofitApplication: Application() {
    private lateinit var webservice: Webservice

    override fun onCreate() {
        super.onCreate()

        initRetrofit()
    }

    private fun initRetrofit() {
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

        runBlocking {
            val data = webservice.getData()
            Log.d(TAG, "Data: $data")
        }
    }

    suspend fun getData() {
        webservice.getData()
    }

    companion object {
        private const val TAG = "HelloRetrofitApplication"
        //private const val BASE_URL = "http://skynote.sandymist.com:7911"
        private const val BASE_URL = "http://10.0.2.2:8000"
    }
}