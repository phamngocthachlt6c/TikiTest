package com.kiemtien.hotlist.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.hotlist.R
import com.kiemtien.hotlist.adapter.PicturesAdapter
import com.kiemtien.hotlist.model.Category
import com.kiemtien.hotlist.model.Picture
import com.kiemtien.hotlist.presenter.PicturesPresenter
import com.kiemtien.hotlist.view.PicturesView
import kotlinx.android.synthetic.main.activity_pictures.*
import kotlinx.android.synthetic.main.activity_pictures.view.*

class PicturesFragment : BaseFragment(), PicturesView {
    private val STATUS_LOADING = 0
    private val STATUS_NOT_EMPTY = 1
    private val STATUS_EMPTY = 2
    private val STATUS_ERROR = 3

    private var picturesPresenter: PicturesPresenter? = null
    private var picturesAdapter: PicturesAdapter? = null
    private var category: Category? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_pictures, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.refreshViewPictures.setOnRefreshListener {
            fetchPictures()
        }
        val linearLayoutManager = GridLayoutManager(context, 2)
        picturesAdapter = PicturesAdapter(linearLayoutManager)
        with(view.rvPictures) {
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
        refreshViewPictures.isRefreshing = false
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
                pgLoadingPictures.visibility = View.GONE
            }

            STATUS_ERROR -> {
                layoutContentPictures.visibility = View.GONE
                pgLoadingPictures.visibility = View.GONE
                tvErrorPictures.visibility = View.VISIBLE
            }
        }
    }

    companion object {

        fun newInstance(category: Category): PicturesFragment {
            val fragment = PicturesFragment()
            fragment.category = category
            return fragment
        }
    }
}
