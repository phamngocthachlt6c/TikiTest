package com.kiemtien.beautylist.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.kiemtien.beautylist.R
import com.kiemtien.beautylist.adapter.MainViewPagerAdapter
import com.kiemtien.beautylist.callback.MainActivityCallback
import com.kiemtien.beautylist.fragment.PicturesFragment
import com.kiemtien.beautylist.model.Category
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.kiemtien.beautylist.config.AppConfig
import com.kiemtien.beautylist.presenter.AppPresenter
import com.kiemtien.beautylist.util.AdsDecider
import android.view.Menu
import android.content.ActivityNotFoundException
import android.net.Uri


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(
            getString(R.string.reward_id),
            AdRequest.Builder().build()
        )
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
            R.id.info -> {
                val intent = Intent(this, PrivacyPolicyActivity::class.java)
                startActivity(intent)
                true
            }
//            R.id.favoirte -> {
//                val intent = Intent(this, FavoritePictureActivity::class.java)
//                startActivity(intent)
//                true
//            }
            R.id.rateApp -> {
                openRating()
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

    private fun openRating() {
        val uri = Uri.parse("market://details?id=" + packageName)
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)
                )
            )
        }

    }
}
