package com.sandymist.helloretrofit

import android.app.Application
import kotlinx.coroutines.runBlocking

class HelloRetrofitApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkModule.initRetrofit()

        runBlocking {
            NetworkModule.getData()
        }
    }

    companion object {
        private const val TAG = "HelloRetrofitApplication"
    }
}