package com.kiemtien.hotlist.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.hotlist.R
import android.support.v7.widget.LinearLayoutManager
import com.kiemtien.hotlist.activity.PicturesActivity
import com.squareup.picasso.Picasso
import com.kiemtien.hotlist.model.Category
import kotlinx.android.synthetic.main.row_category.view.*

class CategoriesAdapter(var layoutManager: LinearLayoutManager) :
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
                .load(category.imageUrl.thumbnail)
                .placeholder(R.drawable.loading_picture)
                .error(R.drawable.loading_picture)
                .into(itemView.imgAvatar)

            itemView.tvTitle.text = category.name
            itemView.tvUpdateAt.text = String.format("%s%s", "Updated on ", category.updateAt)

            itemView.setOnClickListener {
                val nextScreen = Intent(itemView.context, PicturesActivity::class.java)
                nextScreen.putExtra("category", categories[adapterPosition])
                itemView.context.startActivity(nextScreen)
            }
        }
    }
}