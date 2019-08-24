package com.kiemtien.beautylist.activity

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.kiemtien.beautylist.R
import kotlinx.android.synthetic.main.activity_privacy_policy.*


class WebPagePrivacyPolicyActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        supportActionBar?.title = "Privacy Policy"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvGooglePlayService.setOnClickListener {
            openLink("https://policies.google.com/privacy")
        }
        tvGoogleAdMob.setOnClickListener {
            openLink("https://policies.google.com/technologies/ads")
        }
        tvFacebook.setOnClickListener {
            openLink("https://facebook.com/about/privacy")
        }
    }

    private fun openLink(link: String) {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        )
        startActivity(browserIntent)
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
}
