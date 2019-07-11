package com.kiemtien.hotlist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.kiemtien.hotlist.adapter.PicturesAdapter
import com.kiemtien.hotlist.model.Picture
import com.kiemtien.hotlist.presenter.PicturesPresenter
import com.kiemtien.hotlist.view.PicturesView
import com.thachpham.hometest.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pictures.*

class PicturesActivity : AppCompatActivity(), PicturesView {
    private val STATUS_LOADING = 0
    private val STATUS_NOT_EMPTY = 1
    private val STATUS_EMPTY = 2
    private val STATUS_ERROR = 3

    private var picturesPresenter: PicturesPresenter? = null
    private var picturesAdapter: PicturesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pictures)
        refreshViewPictures.setOnRefreshListener {
            fetchPictures()
        }
        val linearLayoutManager = GridLayoutManager(this@PicturesActivity, 2)
        picturesAdapter = PicturesAdapter(linearLayoutManager)
        with(rvPictures) {
            layoutManager = linearLayoutManager
            adapter = picturesAdapter
        }

        picturesPresenter = PicturesPresenter(this)
        fetchPictures()
    }

    private fun fetchPictures() {
        setLayoutStatus(STATUS_LOADING)
        picturesPresenter?.fetchPictures()
    }

    override fun onSuccess(pictures: MutableList<Picture>) {
        picturesAdapter?.setData(pictures)
        refreshViewPictures.isRefreshing = false
        if (pictures.size > 0) {
            setLayoutStatus(STATUS_NOT_EMPTY)
        } else {
            setLayoutStatus(STATUS_EMPTY)
        }
    }

    override fun onError() {
        refreshView.isRefreshing = false
        setLayoutStatus(STATUS_ERROR)
    }

    private fun setLayoutStatus(status: Int) {
        when (status) {
            STATUS_LOADING -> {
                layoutContentPictures.visibility = View.GONE
                pgLoadingPictures.visibility = View.VISIBLE
                tvErrorPictures.visibility = View.GONE
            }
            STATUS_NOT_EMPTY -> {
                layoutContentPictures.visibility = View.VISIBLE
                pgLoadingPictures.visibility = View.GONE
                rvPictures.visibility = View.VISIBLE
            }
            STATUS_EMPTY -> {
                layoutContentPictures.visibility = View.VISIBLE
                rvPictures.visibility = View.GONE
                pgLoading.visibility = View.GONE
            }

            STATUS_ERROR -> {
                layoutContentPictures.visibility = View.GONE
                pgLoadingPictures.visibility = View.GONE
                tvErrorPictures.visibility = View.VISIBLE
            }
        }
    }
}
