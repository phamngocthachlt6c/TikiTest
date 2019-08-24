package com.kiemtien.beautylist.view

import com.kiemtien.beautylist.model.Category

interface CategoriesView {
    fun onSuccess(categories: MutableList<Category>)
    fun onError()
}
