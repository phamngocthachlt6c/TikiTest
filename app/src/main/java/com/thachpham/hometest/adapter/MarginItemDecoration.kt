package com.thachpham.hometest.adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View


class MarginItemDecoration(private val spaceRight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) != 0) {
                left = spaceRight
            }
        }
    }
}