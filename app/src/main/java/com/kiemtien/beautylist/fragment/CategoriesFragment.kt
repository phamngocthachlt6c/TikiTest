package com.kiemtien.beautylist.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.beautylist.R
import com.kiemtien.beautylist.activity.PrivacyPolicyActivity
import com.kiemtien.beautylist.adapter.CategoriesAdapter
import com.kiemtien.beautylist.config.AppConfig
import com.kiemtien.beautylist.model.Category
import com.kiemtien.beautylist.presenter.CategoriesPresenter
import com.kiemtien.beautylist.view.CategoriesView
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : BaseFragment(), CategoriesView {
    private val STATUS_LOADING = 0
    private val STATUS_NOT_EMPTY = 1
    private val STATUS_EMPTY = 2
    private val STATUS_ERROR = 3

    private var categoriesPresenter: CategoriesPresenter? = null
    private var categoriesAdapter: CategoriesAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshView.setOnRefreshListener {
            fetchCategories()
        }
        val linearLayoutManager = LinearLayoutManager(context)
        categoriesAdapter = CategoriesAdapter(linearLayoutManager, mOnDirection)
        with(rvCategories) {
            layoutManager = linearLayoutManager
            adapter = categoriesAdapter
        }

        layoutPrivacyPolicy.visibility =
            if (AppConfig.getInstance().isPrivacyPolicyShortcutLink) View.VISIBLE else View.GONE
        layoutPrivacyPolicy.setOnClickListener {
            val intent = Intent(context, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        btClosePrivacyPolicy.setOnClickListener {
            layoutPrivacyPolicy.visibility = View.GONE
            AppConfig.getInstance().isPrivacyPolicyShortcutLink = false
        }

        categoriesPresenter = CategoriesPresenter(this)
        fetchCategories()
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

    companion object {

        fun newInstance(): CategoriesFragment {
            val fragment = CategoriesFragment()
            return fragment
        }
    }
}
