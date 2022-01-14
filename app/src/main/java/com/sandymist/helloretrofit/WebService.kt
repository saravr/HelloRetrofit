package com.sandymist.helloretrofit

import retrofit2.http.GET

interface Webservice {
    @GET("/")
    suspend fun getData(): Response
}