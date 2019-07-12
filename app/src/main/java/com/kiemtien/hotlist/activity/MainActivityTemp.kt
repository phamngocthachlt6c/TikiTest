package com.kiemtien.hotlist.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.kiemtien.hotlist.R
import com.kiemtien.hotlist.presenter.CategoriesPresenter
import com.kiemtien.hotlist.view.CategoriesView
import kotlinx.android.synthetic.main.activity_main.*
import com.kiemtien.hotlist.adapter.CategoriesAdapter
import com.kiemtien.hotlist.model.Category


class MainActivityTemp : AppCompatActivity(), CategoriesView {
    private val STATUS_LOADING = 0
    private val STATUS_NOT_EMPTY = 1
    private val STATUS_EMPTY = 2
    private val STATUS_ERROR = 3

    private var categoriesPresenter: CategoriesPresenter? = null
    private var categoriesAdapter: CategoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Menu"
        refreshView.setOnRefreshListener {
            fetchCategories()
        }
        val linearLayoutManager = LinearLayoutManager(this@MainActivityTemp)
        categoriesAdapter = CategoriesAdapter(linearLayoutManager)
        with(rvCategories) {
            layoutManager = linearLayoutManager
            adapter = categoriesAdapter
        }

        layoutPrivacyPolicy.setOnClickListener{
            val intent = Intent(this@MainActivityTemp, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        categoriesPresenter = CategoriesPresenter(this)
        fetchCategories()

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713")
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun fetchCategories() {
        setLayoutStatus(STATUS_LOADING)
        categoriesPresenter?.fetchCategories()
    }

    override fun onSuccess(categories: MutableList<Category>) {
        categoriesAdapter?.setData(categories)
        refreshView.isRefreshing = false
        if (categories.size > 0) {
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
                layoutContent.visibility = View.GONE
                pgLoading.visibility = View.VISIBLE
                tvError.visibility = View.GONE
            }
            STATUS_NOT_EMPTY -> {
                layoutContent.visibility = View.VISIBLE
                pgLoading.visibility = View.GONE
                rvCategories.visibility = View.VISIBLE
            }
            STATUS_EMPTY -> {
                layoutContent.visibility = View.VISIBLE
                rvCategories.visibility = View.GONE
                pgLoading.visibility = View.GONE
            }

            STATUS_ERROR -> {
                layoutContent.visibility = View.GONE
                pgLoading.visibility = View.GONE
                tvError.visibility = View.VISIBLE
            }
        }
    }
}
