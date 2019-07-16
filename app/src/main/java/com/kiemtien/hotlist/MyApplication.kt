package com.kiemtien.hotlist

import android.app.Application
import com.kiemtien.hotlist.net.ApiClient
import com.kiemtien.hotlist.net.ApiInterface
import com.kiemtien.hotlist.room.AppDatabase
import androidx.room.Room



class MyApplication : Application() {

    var apiInterface: ApiInterface? = null
        private set

    var appDatabase: AppDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        apiInterface = ApiClient.getClient(applicationContext).create(ApiInterface::class.java)

        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "hotlistdatabase"
        ).build()
    }

    companion object {
        var instance: MyApplication? = null
            private set
    }
}
