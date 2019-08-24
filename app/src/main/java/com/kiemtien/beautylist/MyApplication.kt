package com.kiemtien.beautylist

import android.app.Application
import com.kiemtien.beautylist.net.ApiClient
import com.kiemtien.beautylist.net.ApiInterface
import com.kiemtien.beautylist.room.AppDatabase
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
