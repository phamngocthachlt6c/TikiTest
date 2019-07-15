package com.kiemtien.hotlist.callback

import com.kiemtien.hotlist.model.Category

interface MainActivityCallback {
    fun onGotoCategories()
    fun onGotoPictures(category: Category)
    fun onProcessShowFullAds()
}