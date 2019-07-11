package com.kiemtien.hotlist

import android.app.Application
import com.kiemtien.hotlist.net.ApiClient
import com.kiemtien.hotlist.net.ApiInterface

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
