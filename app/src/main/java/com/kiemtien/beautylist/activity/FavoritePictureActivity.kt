package com.kiemtien.beautylist.activity

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.os.AsyncTask
import android.os.Environment
import com.kiemtien.beautylist.R
import java.io.File
import java.io.FilenameFilter
import android.graphics.BitmapFactory
import android.support.v7.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.kiemtien.beautylist.adapter.PicturesAdapter
import com.kiemtien.beautylist.model.Picture
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoritePictureActivity : AppCompatActivity() {
    private lateinit var picturesAdapter: PicturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val linearLayoutManager = GridLayoutManager(this, 2)
        picturesAdapter = PicturesAdapter(linearLayoutManager)
        with(rvPictures) {
            layoutManager = linearLayoutManager
            adapter = picturesAdapter
        }
        loadFiles()

        MobileAds.initialize(this, getString(R.string.ads_application_id))
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun loadFiles() {
        LoadFilesTask().execute()
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

    private fun loadData(): MutableList<String> {
        val root = File(Environment.getExternalStorageDirectory().absolutePath + "/HotList/Favorite")

        val imageFilter = object : FilenameFilter {
            var f: File? = null
            override fun accept(dir: File, name: String): Boolean {
                if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png")) {
                    return true
                }
                f = File(dir.absolutePath + "/" + name)

                return false
            }
        }
        var mutableList: MutableList<String> = ArrayList()
        try {
            mutableList = root.list(imageFilter).toMutableList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return mutableList
    }

    private inner class LoadFilesTask : AsyncTask<Void, Int, MutableList<Bitmap>>() {
        override fun doInBackground(vararg p0: Void?): MutableList<Bitmap> {
            val listPath = loadData()
            val listBitmap = ArrayList<Bitmap>()
            for (fileName in listPath) {
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(
                        Environment.getExternalStorageDirectory().absolutePath + "/HotList/Favorite/" + fileName, options)
                listBitmap.add(bitmap)
            }
            return listBitmap
        }

        override fun onPostExecute(result: MutableList<Bitmap>?) {
            val pictures = ArrayList<Picture>()
            for (bitmap in result!!.iterator()) {
                val picture = Picture()
                picture.imageBitmap = bitmap
                pictures.add(picture)
            }
            picturesAdapter.setData(pictures)
        }
    }
}
