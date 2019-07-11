package com.kiemtien.hotlist.presenter

import com.kiemtien.hotlist.model.Category
import com.kiemtien.hotlist.model.ImageUrl
import com.kiemtien.hotlist.view.CategoriesView

class CategoriesPresenter(var categoriesView: CategoriesView) {

    fun fetchCategories() {
//        MyApplication.instance?.apiInterface?.fetCategories()
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(object : DisposableObserver<Response<DataResponse<MutableList<Category>>>>() {
//                override fun onNext(value: Response<DataResponse<MutableList<Category>>>?) {
//                    keywordListView.onSuccess(value?.body()?.data!!)
//                }
//
//                override fun onComplete() {
//                }
//
//                override fun onError(e: Throwable?) {
//                    keywordListView.onError()
//                }
//            })
        categoriesView.onSuccess(getDummyData())
    }

    private fun getDummyData(): MutableList<Category> {
        val categories = ArrayList<Category>()
        var category = Category()
        category.id = "1"
        category.name = "category1"
        category.imageUrl = ImageUrl()
        category.imageUrl.thumbnail = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        category.updateAt = "Jun 24"
        categories.add(category)
        category = Category()

        category.id = "2"
        category.name = "category2"
        category.imageUrl = ImageUrl()
        category.imageUrl.thumbnail = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        category.updateAt = "7/20/2019"
        categories.add(category)

        category = Category()
        category.id = "3"
        category.name = "category3"
        category.imageUrl = ImageUrl()
        category.imageUrl.thumbnail = "a"
        category.updateAt = "7/20/2019"
        categories.add(category)

        return categories
    }
}
