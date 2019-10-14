package com.kiemtien.beautylist.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.kiemtien.beautylist.R
import com.kiemtien.beautylist.adapter.DetailPicturesAdapter
import com.kiemtien.beautylist.model.Picture
import kotlinx.android.synthetic.main.activity_picture_detail.*
import android.support.v7.widget.PagerSnapHelper
import android.util.Log
import android.view.View
import android.view.animation.*
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.ShareApi
import com.facebook.share.Sharer
import com.kiemtien.beautylist.util.DownloadUtils
import com.kiemtien.beautylist.util.PermissionUtils
import com.facebook.share.model.SharePhoto
import com.facebook.share.widget.ShareDialog
import com.facebook.share.model.ShareMediaContent
import java.io.File


class PictureDetailActivity : AppCompatActivity() {
    companion object {
        var sPictures: ArrayList<Picture>? = null
    }

    private var detailPicturesAdapter: DetailPicturesAdapter? = null
    private var isShowControlButton = true
    private val mHandler = Handler()
    private var mPlayListThread: PlayListThread? = null
    private var mCallbackManager: CallbackManager? = null
    private var mShareDialog: ShareDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_detail)
        initFacebookSharing()
        supportActionBar?.hide()
        initUI()
        setupUIAction()
    }

    private fun initFacebookSharing() {
        mCallbackManager = CallbackManager.Factory.create()
        mShareDialog = ShareDialog(this)
        mShareDialog?.registerCallback(mCallbackManager, object : FacebookCallback<Sharer.Result> {
            override fun onSuccess(result: Sharer.Result?) {
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
                Log.e("aaa", error.toString())
                if (error != null && error.message.equals("null")) {
                    // Don't use the app for sharing in case of null-error
                    shareFacebook(ShareDialog.Mode.WEB)
                }
            }

        })
    }

    private fun initUI() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        detailPicturesAdapter = DetailPicturesAdapter(linearLayoutManager)
        with(rcvListImage) {
            layoutManager = linearLayoutManager
            adapter = detailPicturesAdapter
        }
        // add pager behavior
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rcvListImage)

        var pictures: ArrayList<Picture> = ArrayList()
        if (sPictures != null) {
            pictures.addAll(sPictures!!)
            sPictures = null
            btFavorite.visibility = View.GONE
            btDownload.visibility = View.GONE
        } else {
            pictures = intent.getParcelableArrayListExtra("pictures")
        }
        detailPicturesAdapter?.setData(pictures)
        val index = intent.getIntExtra("current_index", 0)
        if (index < pictures.size) {
            linearLayoutManager.scrollToPositionWithOffset(index, 0)
        }
    }

    private fun setupUIAction() {
        btBack.setOnClickListener {
            finish()
        }
        setActionForClickHidingControlLayout()
        btDownload.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //Permission is granted
                    downloadImage()
                } else {
                    if (PermissionUtils.neverAskAgainSelected(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        displayNeverAskAgainDialog()
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                    }
                }
            }
        }
        btFavorite.setOnClickListener {
            //            shareFacebook(ShareDialog.Mode.AUTOMATIC)
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    //Permission is granted
                    downloadImageToFavoriteFolder()
                } else {
                    if (PermissionUtils.neverAskAgainSelected(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        displayNeverAskAgainDialog()
                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE), 2)
                    }
                }
            }
        }
    }

    private fun shareFacebook(mode: ShareDialog.Mode) {
        DownloadUtils.downloadImageToBitmap("https://ttol.vietnamnetjsc.vn/images/2019/03/06/08/06/Hot-girl-Han-Quoc-14.jpg",
                object : DownloadUtils.DownloadBitmapCallback {
                    override fun onSuccess(bitmap: Bitmap?) {
                        val sharePhoto1 = SharePhoto.Builder()
                                .setBitmap(bitmap)
                                .build()
                        val shareContent = ShareMediaContent.Builder()
                                .addMedium(sharePhoto1)
                                .build()
                        mShareDialog?.show(shareContent, mode)
                        ShareApi.share(shareContent, null)
                    }

                    override fun onFailed() {
                    }
                })
    }

    private fun displayNeverAskAgainDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("We need permission for performing necessary task. Please permit the permission through "
                + "Settings screen.\n\nSelect Permissions -> Enable permission")
        builder.setCancelable(false)
        builder.setPositiveButton("Permit Manually") { dialog, which ->
            dialog.dismiss()
            val intent = Intent()
            intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadImage()
            } else {
                PermissionUtils.setShouldShowStatus(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        } else if (requestCode == 2) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloadImageToFavoriteFolder()
            } else {
                PermissionUtils.setShouldShowStatus(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun downloadImageToFavoriteFolder() {
        val position = (rcvListImage.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
        val picture = detailPicturesAdapter?.getPicture(position)
        DownloadUtils.download(picture?.imageUrl?.large, picture?.name, "HotList" + File.separator + "Favorite",
                object : DownloadUtils.DownloadingCallback {
                    override fun onFinished() {
                        Toast.makeText(this@PictureDetailActivity, "Photo was saved in Favorite list", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailed() {
                        Toast.makeText(this@PictureDetailActivity, "An error occurs, Photo cannot download", Toast.LENGTH_LONG).show()
                    }
                })
    }

    private fun downloadImage() {
        val position = (rcvListImage.layoutManager as LinearLayoutManager)
                .findFirstVisibleItemPosition()
        val picture = detailPicturesAdapter?.getPicture(position)
        DownloadUtils.download(picture?.imageUrl?.large, picture?.name, "HotList",
                object : DownloadUtils.DownloadingCallback {
                    override fun onFinished() {
                        Toast.makeText(this@PictureDetailActivity, "Photo was saved in /HostList", Toast.LENGTH_LONG).show()
                    }

                    override fun onFailed() {
                        Toast.makeText(this@PictureDetailActivity, "An error occurs, Photo cannot download", Toast.LENGTH_LONG).show()
                    }
                })
    }

    private fun setActionForClickHidingControlLayout() {
        val animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)
        animationFadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                layoutControl.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        val animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        detailPicturesAdapter?.setOnItemClick(object : DetailPicturesAdapter.OnItemClick {
            override fun onClick() {
                if (isShowControlButton) {
                    layoutControl.startAnimation(animationFadeOut)
                } else {
                    layoutControl.visibility = View.VISIBLE
                    layoutControl.startAnimation(animationFadeIn)
                    stopListImage()
                }
                isShowControlButton = !isShowControlButton
            }
        })

        btPlayAllList.setOnClickListener {
            playListImage()
            layoutControl.startAnimation(animationFadeOut)
            isShowControlButton = false
        }
    }

    private fun playListImage() {
        if (mPlayListThread?.isAlive == true) {
            mPlayListThread?.stopProcess()
        }
        mPlayListThread = PlayListThread()
        mPlayListThread?.start()
    }

    private fun stopListImage() {
        if (mPlayListThread?.isAlive == true) {
            mPlayListThread?.stopProcess()
        }
    }

    inner class PlayListThread : Thread() {
        var isRunning = false
        override fun run() {
            while (isRunning) {
                Thread.sleep(3000)
                Thread(Runnable {
                    mHandler.post {
                        try {
                            rcvListImage.scrollToPosition((rcvListImage.layoutManager as LinearLayoutManager)
                                    .findFirstVisibleItemPosition() + 1)
                        } catch (exception: Exception) {
                            exception.printStackTrace()
                        }
                    }
                }).start()
            }
        }

        override fun start() {
            isRunning = true
            super.start()
        }

        fun stopProcess() {
            isRunning = false
        }
    }

    override fun onPause() {
        super.onPause()
        layoutControl.visibility = View.VISIBLE
        stopListImage()
    }
}
