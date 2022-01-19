package com.sandymist.helloretrofit

import com.sandymist.helloretrofit.model.ContactsResponse
import retrofit2.http.GET

interface Webservice {
    @GET("/contacts")
    suspend fun getContacts(): ContactsResponse
}