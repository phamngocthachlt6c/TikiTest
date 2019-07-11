package com.kiemtien.hotlist.view

import com.kiemtien.hotlist.model.Category

interface CategoriesView {
    fun onSuccess(categories: MutableList<Category>)
    fun onError()
}
