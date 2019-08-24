package com.kiemtien.beautylist.presenter

import com.kiemtien.beautylist.MyApplication
import com.kiemtien.beautylist.config.AppConfig
import com.kiemtien.beautylist.model.CommonConfig
import com.kiemtien.beautylist.model.DataResponse

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
