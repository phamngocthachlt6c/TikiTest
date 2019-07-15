package com.kiemtien.hotlist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.kiemtien.hotlist.R
import com.kiemtien.hotlist.adapter.MainViewPagerAdapter
import com.kiemtien.hotlist.callback.MainActivityCallback
import com.kiemtien.hotlist.fragment.PicturesFragment
import com.kiemtien.hotlist.model.Category
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.kiemtien.hotlist.config.AppConfig
import com.kiemtien.hotlist.presenter.AppPresenter
import com.kiemtien.hotlist.util.AdsDecider


class MainActivity : AppCompatActivity(), MainActivityCallback {

    private var mainViewPagerAdapter: MainViewPagerAdapter? = null
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private var adsDecider: AdsDecider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Init Config
        AppConfig.getInstance(this)
        AppPresenter().fetchConfig()

        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Menu"

        mainViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager, this)
        mainViewPager.adapter = mainViewPagerAdapter

        MobileAds.initialize(this, getString(R.string.ads_application_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd?.adUnitId = getString(R.string.interstitial_id)
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
        setListenerInterstitialAd()

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        setAwardVideoAdListener()
        loadRewardedVideoAd()

        adsDecider = AdsDecider()
    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(getString(R.string.reward_id),
                AdRequest.Builder().build())
    }

    override fun onGotoCategories() {
        mainViewPager.currentItem = 0
        supportActionBar?.title = "Menu"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onGotoPictures(category: Category) {
        (mainViewPagerAdapter?.getItem(1) as PicturesFragment).reload(category)
        mainViewPager.currentItem = 1
        supportActionBar?.title = category.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onProcessShowFullAds() {
        if (adsDecider!!.canShowAds()) {
            if (adsDecider!!.showWhat() == 0) {
                if (mInterstitialAd!!.isLoaded) {
                    mInterstitialAd!!.show()
                } else {
                    if (mRewardedVideoAd.isLoaded) {
                        mRewardedVideoAd.show()
                    }
                }
            } else {
                if (mRewardedVideoAd.isLoaded) {
                    mRewardedVideoAd.show()
                } else {
                    if (mInterstitialAd!!.isLoaded) {
                        mInterstitialAd!!.show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        when (mainViewPager.currentItem) {
            0 -> {
                finish()
            }
            1 -> {
                onGotoCategories()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setListenerInterstitialAd() {
        mInterstitialAd?.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                mInterstitialAd?.loadAd(AdRequest.Builder().build())
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                mInterstitialAd?.loadAd(AdRequest.Builder().build())
            }
        }
    }

    private fun setAwardVideoAdListener() {
        mRewardedVideoAd.rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onRewarded(reward: RewardItem) {
            }

            override fun onRewardedVideoAdLeftApplication() {
            }

            override fun onRewardedVideoAdClosed() {
                loadRewardedVideoAd()
            }

            override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
                loadRewardedVideoAd()
            }

            override fun onRewardedVideoAdLoaded() {
            }

            override fun onRewardedVideoAdOpened() {
            }

            override fun onRewardedVideoStarted() {
            }

            override fun onRewardedVideoCompleted() {
            }
        }
    }
}
