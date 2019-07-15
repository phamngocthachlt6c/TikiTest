package com.kiemtien.hotlist.presenter

import com.kiemtien.hotlist.MyApplication
import com.kiemtien.hotlist.config.AppConfig
import com.kiemtien.hotlist.model.CommonConfig
import com.kiemtien.hotlist.model.DataResponse

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class AppPresenter {
    fun fetchConfig() {
        MyApplication.instance?.apiInterface?.fetCommonConfig()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : DisposableObserver<Response<DataResponse<CommonConfig>>>() {
                    override fun onNext(value: Response<DataResponse<CommonConfig>>?) {
                        val config = value?.body()?.data
                        if (config != null) {
                            AppConfig.getInstance().maxNumberDelayAd = config.maxNumberDelay
                            AppConfig.getInstance().maxTimeDelayAd = config.maxTimeDelay
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable?) {
                    }
                })
    }
}
