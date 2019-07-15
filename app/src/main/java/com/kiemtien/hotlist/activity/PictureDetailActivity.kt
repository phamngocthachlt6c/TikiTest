package com.kiemtien.hotlist.activity

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kiemtien.hotlist.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_picture_detail.*

class PictureDetailActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_detail)
        supportActionBar?.hide()

        btClose.setOnClickListener{
            finish()
        }

        val link = intent.getStringExtra("link")
        if (link != null && !link.equals("")) {
            Picasso.with(this)
                    .load(link)
                    .placeholder(R.drawable.loading_picture)
                    .error(R.drawable.loading_picture)
                    .into(imgPicture)
        }
    }
}
