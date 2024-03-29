package com.kiemtien.beautylist.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kiemtien.beautylist.R
import android.support.v7.widget.LinearLayoutManager
import com.kiemtien.beautylist.activity.FavoritePictureActivity
import com.kiemtien.beautylist.activity.PictureDetailActivity
import com.squareup.picasso.Picasso
import com.kiemtien.beautylist.model.Picture
import kotlinx.android.synthetic.main.row_picture.view.*

class PicturesAdapter(var layoutManager: LinearLayoutManager) :
        RecyclerView.Adapter<PicturesAdapter.PictureVH>() {
    private var pictures: ArrayList<Picture> = ArrayList()
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
        fun bindView(picture: Picture) {
            if (picture.imageUrl != null && picture.imageUrl.medium != null && !picture.imageUrl.medium.equals("")) {
                Picasso.with(itemView.context)
                        .load(picture.imageUrl.medium)
                        .placeholder(R.drawable.loading_picture2)
                        .error(R.drawable.loading_picture2)
                        .into(itemView.imgAvatar)
            } else if (picture.imageBitmap != null) {
                itemView.imgAvatar.setImageBitmap(picture.imageBitmap)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, PictureDetailActivity::class.java)
                intent.putExtra("current_index", adapterPosition)
                if (pictures[adapterPosition].imageBitmap == null) {
                    intent.putParcelableArrayListExtra("pictures", pictures)
                } else {
                    PictureDetailActivity.sPictures = pictures
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}