package com.kiemtien.beautylist.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.beautylist.R
import android.support.v7.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.kiemtien.beautylist.model.Picture
import kotlinx.android.synthetic.main.row_picture_detail.view.*

class DetailPicturesAdapter(var layoutManager: LinearLayoutManager) :
        RecyclerView.Adapter<DetailPicturesAdapter.PictureVH>() {
    private var pictures: MutableList<Picture> = ArrayList()
    private var mOnItemClick: OnItemClick? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PictureVH {
        return PictureVH(LayoutInflater.from(viewGroup.context).inflate(R.layout.row_picture_detail, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(viewHolder: PictureVH, position: Int) {
        viewHolder.bindView(pictures[position])
    }

    fun setData(pictures: ArrayList<Picture>) {
        with(this.pictures) {
            clear()
            addAll(pictures)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClick(onItemClick: OnItemClick) {
        mOnItemClick = onItemClick
    }

    inner class PictureVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(picture: Picture) {
            if (picture.imageUrl != null && picture.imageUrl.medium != null && !picture.imageUrl.medium.equals("")) {
                Picasso.with(itemView.context)
                        .load(picture.imageUrl.large)
                        .placeholder(R.drawable.loading_picture2)
                        .error(R.drawable.loading_picture2)
                        .into(itemView.imgPicture)
            }
            itemView.setOnClickListener {
                mOnItemClick?.onClick()
            }
        }
    }

    interface OnItemClick {
        fun onClick()
    }
}