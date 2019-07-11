package com.kiemtien.hotlist.view

import com.kiemtien.hotlist.model.Picture

interface PicturesView {
    fun onSuccess(pictures : MutableList<Picture>)
    fun onError()
}
