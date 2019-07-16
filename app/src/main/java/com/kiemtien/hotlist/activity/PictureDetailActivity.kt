package com.kiemtien.hotlist.activity

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kiemtien.hotlist.MyApplication
import com.kiemtien.hotlist.R
import com.kiemtien.hotlist.model.FavoritePicture
import com.kiemtien.hotlist.model.Picture
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_picture_detail.*

class PictureDetailActivity() : AppCompatActivity() {

    var isFavorite = false
    var picture : Picture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_detail)
        supportActionBar?.hide()

        btClose.setOnClickListener {
            finish()
        }

        picture = intent.getParcelableExtra("picture")
        if (picture != null && !picture?.imageUrl?.large.equals("")) {
            tvPictureName.text = picture?.name

            Picasso.with(this)
                .load(picture?.imageUrl?.large)
                .placeholder(R.drawable.loading_picture)
                .error(R.drawable.loading_picture)
                .into(imgPicture)

//            SearchFavoritePictureTask().execute(picture?.id)
            btFavorite.setOnClickListener {

                if (!isFavorite) {
                    val favoritePicture = FavoritePicture()
                    favoritePicture.name = picture?.name
                    favoritePicture.pictureId = picture?.id
                    favoritePicture.smallLink = picture?.imageUrl?.small
                    favoritePicture.largeLink = picture?.imageUrl?.large

                    MyApplication.instance?.appDatabase?.favoritePictureDao()?.insert(favoritePicture)
                        ?.subscribeOn(Schedulers.io())
                        ?.observeOn(AndroidSchedulers.mainThread())
                        ?.subscribe(object : DisposableCompletableObserver() {
                            override fun onComplete() {
                                btFavorite.setImageResource(R.drawable.ic_favorite_pink)
                                isFavorite = true
                            }

                            override fun onError(e: Throwable?) {
                            }
                        })
                } else {
//                    MyApplication.instance?.appDatabase?.favoritePictureDao()?.deleteFavoriteById(picture.id)
//                        ?.subscribeOn(Schedulers.io())
//                        ?.observeOn(AndroidSchedulers.mainThread())
//                        ?.subscribe(object : DisposableCompletableObserver() {
//                            override fun onComplete() {
//                                btFavorite.setImageResource(R.drawable.ic_favorite_border_pink)
//                                isFavorite = false
//                            }
//
//                            override fun onError(e: Throwable?) {
//                            }
//                        })
                }
            }
        }
    }

//    private inner class SearchFavoritePictureTask : AsyncTask<String, Int, FavoritePicture>() {
//        override fun doInBackground(vararg p0: String?): FavoritePicture {
////            return MyApplication.instance?.appDatabase?.favoritePictureDao()?.findFavoriteById(picture?.id)
//        }
//
//        override fun onPostExecute(result: FavoritePicture?) {
//            if (result != null) {
//                btFavorite.setImageResource(R.drawable.ic_favorite_pink)
//                isFavorite = true
//            }
//        }
//    }
}
