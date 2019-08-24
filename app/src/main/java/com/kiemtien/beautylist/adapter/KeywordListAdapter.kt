package com.kiemtien.beautylist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.beautylist.R
import kotlinx.android.synthetic.main.row_keyword.view.*
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v7.widget.LinearLayoutManager
import com.kiemtien.beautylist.util.StringUtils


class KeywordListAdapter(var keywordColors: IntArray, var layoutManager: LinearLayoutManager) :
    RecyclerView.Adapter<KeywordListAdapter.KeywordVH>() {
    private val MIDDLE_INDEX = Integer.MAX_VALUE / 2
    private var keywords: MutableList<String> = ArrayList()
    private var indexColors = 0
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): KeywordVH {
        return KeywordVH(LayoutInflater.from(viewGroup.context).inflate(R.layout.row_keyword, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return if (keywords.size == 0) 0 else Integer.MAX_VALUE
    }

    override fun onBindViewHolder(viewHolder: KeywordVH, position: Int) {
        viewHolder.bindView(keywords[getIndexOnList(position)])
    }

    fun setData(keywords: MutableList<String>) {
        for (i in 0 until keywords.size) {
            keywords[i] = StringUtils.getKeywordOptimized(keywords[i])
        }
        with(this.keywords) {
            clear()
            addAll(keywords)
        }
        notifyDataSetChanged()
        layoutManager.scrollToPositionWithOffset(MIDDLE_INDEX, 0)
    }

    private fun getIndexOnList(fakeIndex: Int): Int {
        val intervalMiddleIndexFakeIndex = fakeIndex - MIDDLE_INDEX
        if (intervalMiddleIndexFakeIndex == 0) {
            return 0
        }
        return if (intervalMiddleIndexFakeIndex > 0) {
            val temp = intervalMiddleIndexFakeIndex % keywords.size
            if (temp != keywords.size) temp else 0
        } else {
            val temp = keywords.size - Math.abs(intervalMiddleIndexFakeIndex % keywords.size)
            if (temp != keywords.size) temp else 0
        }
    }

    inner class KeywordVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(keyword: String) {
            itemView.tvKeyword.text = keyword
            if (indexColors >= keywordColors.size) {
                indexColors = 0
            }
            val color = keywordColors[indexColors]
            indexColors++
            val colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_OVER)
            itemView.mainLayout.background.colorFilter = colorFilter
        }
    }
}