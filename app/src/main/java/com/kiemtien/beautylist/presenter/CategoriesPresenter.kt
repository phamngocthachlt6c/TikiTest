package com.kiemtien.beautylist.presenter

import com.kiemtien.beautylist.MyApplication
import com.kiemtien.beautylist.model.Category
import com.kiemtien.beautylist.model.DataResponse
import com.kiemtien.beautylist.model.ImageUrl
import com.kiemtien.beautylist.view.CategoriesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class CategoriesPresenter(var categoriesView: CategoriesView) {

    fun fetchCategories() {
        MyApplication.instance?.apiInterface?.fetCategories()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableObserver<Response<DataResponse<MutableList<Category>>>>() {
                override fun onNext(value: Response<DataResponse<MutableList<Category>>>?) {
                    categoriesView.onSuccess(value?.body()?.data!!)
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable?) {
                    categoriesView.onError()
                }
            })
    }
}
