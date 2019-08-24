package com.kiemtien.beautylist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.beautylist.R
import android.support.v7.widget.LinearLayoutManager
import com.kiemtien.beautylist.callback.MainActivityCallback
import com.squareup.picasso.Picasso
import com.kiemtien.beautylist.model.Category
import kotlinx.android.synthetic.main.row_category.view.*

class CategoriesAdapter(var layoutManager: LinearLayoutManager, var onDirection: MainActivityCallback?) :
        RecyclerView.Adapter<CategoriesAdapter.CategoryVH>() {
    private var categories: MutableList<Category> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CategoryVH {
        return CategoryVH(LayoutInflater.from(viewGroup.context).inflate(R.layout.row_category, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(viewHolder: CategoryVH, position: Int) {
        viewHolder.bindView(categories[position])
    }

    fun setData(keywords: MutableList<Category>) {
        with(this.categories) {
            clear()
            addAll(keywords)
        }
        notifyDataSetChanged()
    }

    inner class CategoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(category: Category) {
            Picasso.with(itemView.context)
                    .load(category.imageUrl.small)
                    .placeholder(R.drawable.loading_picture)
                    .error(R.drawable.loading_picture)
                    .into(itemView.imgAvatar)

            itemView.tvTitle.text = category.name
            itemView.tvUpdateAt.text = String.format("%s%s", "Updated on ", category.updateAt)
            itemView.tvUpdateAt.setTextColor(if(category.isTodayUpdate) R.color.row_category_text_update_at else R.color.black)

            itemView.setOnClickListener {
                onDirection?.onGotoPictures(categories[adapterPosition])
                onDirection?.onProcessShowFullAds()
            }
        }
    }
}