package com.sandymist.helloretrofit

import android.util.Log
import com.google.gson.Gson
import com.jayway.jsonpath.JsonPath
import com.sandymist.helloretrofit.model.ContactsResponse
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jayway.jsonpath.DocumentContext




object NetworkModule {
    private lateinit var webservice: Webservice

    fun initRetrofit() {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(ResponseInterceptor())
        builder.addInterceptor(loggingInterceptor)
        val okHttpClient = builder.build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

        webservice = retrofit.create(Webservice::class.java)
    }

    suspend fun getData() {
        val data = webservice.getContacts()
        Log.d(TAG, "Contacts Data: $data")
    }

    private const val TAG = "NetworkModule"
    private const val BASE_URL = "http://10.0.2.2:8000/"
}

class ResponseInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e(TAG, "Intercepted URL: " + chain.request().url)
        val response = chain.proceed(chain.request())
        var responseString = response.body?.string()

        val parsedDataContext: DocumentContext = JsonPath.parse(responseString)
        parsedDataContext.set("$..lastName", "FooBar")
        responseString = parsedDataContext.jsonString()
        Log.e(TAG, "++++ Modified payload: $responseString")

        val respAsJson = Gson().fromJson(responseString, ContactsResponse::class.java)

        /*
        val latitudes = JsonPath.read<List<Double>>(responseString, "$..latitude")
        Log.e(TAG, "++++ LATITUDES: $latitudes")

        val lastNames = JsonPath.read<List<String>>(responseString, "$..lastName")
        Log.e(TAG, "++++ LastNames: $lastNames")
        */

        val modifiedData = respAsJson.data?.map {
            it?.copy(lastName = it.lastName?.uppercase())
        }
        val modifiedRespAsJson = respAsJson.copy(data = modifiedData)
        val modifiedResp = Gson().toJson(modifiedRespAsJson)
        val modifiedBody = modifiedResp.toResponseBody("application/json".toMediaType())
        return response.newBuilder()
            .body(modifiedBody)
            .build()
    }

    companion object {
        private const val TAG = "ResponseInterceptor"
    }
}