package com.kiemtien.beautylist.view

import com.kiemtien.beautylist.model.Picture

interface PicturesView {
    fun onSuccess(pictures : MutableList<Picture>)
    fun onError()
}
