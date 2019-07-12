package com.kiemtien.hotlist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.hotlist.R
import android.support.v7.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.kiemtien.hotlist.model.Picture
import kotlinx.android.synthetic.main.row_picture.view.*

class PicturesAdapter(var layoutManager: LinearLayoutManager) :
    RecyclerView.Adapter<PicturesAdapter.PictureVH>() {
    private var pictures: MutableList<Picture> = ArrayList()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PictureVH {
        return PictureVH(LayoutInflater.from(viewGroup.context).inflate(R.layout.row_picture, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(viewHolder: PictureVH, position: Int) {
        viewHolder.bindView(pictures[position])
    }

    fun setData(keywords: MutableList<Picture>) {
        with(this.pictures) {
            clear()
            addAll(keywords)
        }
        notifyDataSetChanged()
    }

    inner class PictureVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(picture : Picture) {
            Picasso.with(itemView.context)
                .load(picture.imageUrl.thumbnail)
                .placeholder(R.drawable.loading_picture)
                .error(R.drawable.loading_picture)
                .into(itemView.imgAvatar)
        }
    }
}