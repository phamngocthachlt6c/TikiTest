package com.kiemtien.hotlist.presenter

import com.kiemtien.hotlist.MyApplication
import com.kiemtien.hotlist.model.DataResponse
import com.kiemtien.hotlist.model.ImageUrl
import com.kiemtien.hotlist.model.Picture
import com.kiemtien.hotlist.view.PicturesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class PicturesPresenter(var picturesView: PicturesView) {

    fun fetchPictures(categoryId: String?) {
        MyApplication.instance?.apiInterface?.fetPictures(categoryId)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableObserver<Response<DataResponse<MutableList<Picture>>>>() {
                override fun onNext(value: Response<DataResponse<MutableList<Picture>>>?) {
                    picturesView.onSuccess(value?.body()?.data!!)
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable?) {
                    picturesView.onError()
                }
            })
//        picturesView.onSuccess(getDummyData())
    }

    private fun getDummyData(): MutableList<Picture> {
        val pictures = ArrayList<Picture>()
        var picture = Picture()
        picture.id = "1"
        picture.name = "category1"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)
        picture = Picture()

        picture.id = "2"
        picture.name = "category2"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)
        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)
        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)
        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)

        picture = Picture()
        picture.id = "3"
        picture.name = "category3"
        picture.imageUrl = ImageUrl()
        picture.imageUrl.small = "a"
        picture.imageUrl.large = "https://i.ytimg.com/vi/ktlQrO2Sifg/maxresdefault.jpg"
        pictures.add(picture)



        return pictures
    }
}
