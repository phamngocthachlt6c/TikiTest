package com.thachpham.hometest.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.thachpham.hometest.presenter.KeywordListPresenter
import com.thachpham.hometest.adapter.KeywordListAdapter
import com.thachpham.hometest.adapter.MarginItemDecoration
import com.thachpham.hometest.view.KeywordListView
import kotlinx.android.synthetic.main.activity_main.*
import com.thachpham.hometest.R


class MainActivity : AppCompatActivity(), KeywordListView {
    private val STATUS_LOADING = 0
    private val STATUS_NOT_EMPTY = 1
    private val STATUS_EMPTY = 2
    private val STATUS_ERROR = 3

    private var keywordListPresenter: KeywordListPresenter? = null
    private var keywordListAdapter: KeywordListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        refreshView.setOnRefreshListener {
            fetchKeywordList()
        }
        val linearLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        keywordListAdapter = KeywordListAdapter(getKeywordColors(), linearLayoutManager)
        with(rvKeywords) {
            addItemDecoration(MarginItemDecoration(resources.getDimension(R.dimen.row_keyword_right_space).toInt()))
            layoutManager = linearLayoutManager
            adapter = keywordListAdapter
        }

        keywordListPresenter = KeywordListPresenter(this)
        fetchKeywordList()
    }

    private fun fetchKeywordList() {
        setLayoutStatus(STATUS_LOADING)
        keywordListPresenter?.fetchKeywordList()
    }

    override fun onSuccess(keywords: MutableList<String>) {
        keywordListAdapter?.setData(keywords)
        refreshView.isRefreshing = false
        if (keywords.size > 0) {
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
                tvEmpty.visibility = View.GONE
                pgLoading.visibility = View.GONE
                rvKeywords.visibility = View.VISIBLE
            }
            STATUS_EMPTY -> {
                layoutContent.visibility = View.VISIBLE
                rvKeywords.visibility = View.GONE
                tvEmpty.visibility = View.VISIBLE
                pgLoading.visibility = View.GONE
            }

            STATUS_ERROR -> {
                layoutContent.visibility = View.GONE
                pgLoading.visibility = View.GONE
                tvError.visibility = View.VISIBLE
            }
        }
    }

    private fun getKeywordColors(): IntArray {
        val ta = resources.obtainTypedArray(R.array.keyword_colors)
        val colors = IntArray(ta.length())
        for (i in 0 until ta.length()) {
            colors[i] = ta.getColor(i, 0)
        }
        ta.recycle()

        return colors
    }
}
