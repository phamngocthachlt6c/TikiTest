package com.kiemtien.hotlist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.kiemtien.hotlist.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Menu"



        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}
