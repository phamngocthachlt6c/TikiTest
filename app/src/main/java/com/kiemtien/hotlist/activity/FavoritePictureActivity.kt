package com.kiemtien.hotlist.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.room.Room
import com.kiemtien.hotlist.model.FavoritePicture
import com.kiemtien.hotlist.room.AppDatabase
import com.kiemtien.hotlist.room.FavoritePictureDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import android.os.AsyncTask
import android.util.Log
import com.kiemtien.hotlist.MyApplication
import com.kiemtien.hotlist.R


class FavoritePictureActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_detail)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()
    }

    private fun loadData() {
        DownloadFilesTask().execute()
    }

    private inner class DownloadFilesTask : AsyncTask<Void, Int, MutableList<FavoritePicture>>() {
        override fun doInBackground(vararg p0: Void?): MutableList<FavoritePicture> {
            return MyApplication.instance?.appDatabase?.favoritePictureDao()!!.allFavorites
        }

        override fun onPostExecute(result: MutableList<FavoritePicture>?) {
            Log.d("aaa", result?.get(0)?.name)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
