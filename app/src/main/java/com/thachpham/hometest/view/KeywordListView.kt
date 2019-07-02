package com.thachpham.hometest.view

interface KeywordListView {
    fun onSuccess(keywords: MutableList<String>)
    fun onError()
}
