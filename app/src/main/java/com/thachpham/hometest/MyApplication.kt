package com.thachpham.hometest

import android.app.Application
import com.thachpham.hometest.net.ApiClient
import com.thachpham.hometest.net.ApiInterface

class MyApplication : Application() {

    var apiInterface: ApiInterface? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        apiInterface = ApiClient.getClient(applicationContext).create(ApiInterface::class.java)
    }

    companion object {
        var instance: MyApplication? = null
            private set
    }
}
