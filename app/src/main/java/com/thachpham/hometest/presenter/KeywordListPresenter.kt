package com.thachpham.hometest.presenter

import com.thachpham.hometest.MyApplication
import com.thachpham.hometest.view.KeywordListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class KeywordListPresenter(var keywordListView: KeywordListView) {

    fun fetchKeywordList() {
        MyApplication.instance?.apiInterface?.keywordList
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableObserver<Response<MutableList<String>>>() {
                override fun onNext(value: Response<MutableList<String>>?) {
                    keywordListView.onSuccess(value?.body()!!)
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable?) {
                    keywordListView.onError()
                }
            })
    }
}
