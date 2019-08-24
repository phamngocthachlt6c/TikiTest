package com.kiemtien.beautylist.callback

import com.kiemtien.beautylist.model.Category

interface MainActivityCallback {
    fun onGotoCategories()
    fun onGotoPictures(category: Category)
    fun onProcessShowFullAds()
}