package com.kiemtien.beautylist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.kiemtien.beautylist.R
import com.kiemtien.beautylist.adapter.DetailPicturesAdapter
import com.kiemtien.beautylist.model.Picture
import kotlinx.android.synthetic.main.activity_picture_detail.*
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import android.view.animation.*


class PictureDetailActivity() : AppCompatActivity() {
    var detailPicturesAdapter: DetailPicturesAdapter? = null
    var isShowControlButton = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_detail)
        supportActionBar?.hide()
        initUI()
        setupUIAction()
    }

    private fun initUI() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailPicturesAdapter = DetailPicturesAdapter(linearLayoutManager)
        with(rcvListImage) {
            layoutManager = linearLayoutManager
            adapter = detailPicturesAdapter
        }
        // add pager behavior
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rcvListImage)

        val pictures: ArrayList<Picture> = intent.getParcelableArrayListExtra("pictures")
        detailPicturesAdapter?.setData(pictures)
        val index = intent.getIntExtra("current_index", 0)
        if (index < pictures.size) {
            linearLayoutManager.scrollToPositionWithOffset(index, 0)
        }
    }

    private fun setupUIAction() {
        btBack.setOnClickListener {
            finish()
        }

        setActionForClickHidingControlLayout()
    }

    private fun setActionForClickHidingControlLayout() {
        val animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        animationFadeOut.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                layoutControl.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        val animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        detailPicturesAdapter?.setOnItemClick(object : DetailPicturesAdapter.OnItemClick {
            override fun onClick() {
                if (isShowControlButton) {
                    layoutControl.startAnimation(animationFadeOut)
                } else {
                    layoutControl.visibility = View.VISIBLE
                    layoutControl.startAnimation(animationFadeIn)
                }
                isShowControlButton = !isShowControlButton
            }
        })
    }
}
