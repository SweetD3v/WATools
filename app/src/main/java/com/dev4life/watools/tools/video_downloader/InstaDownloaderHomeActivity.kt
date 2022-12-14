package com.dev4life.watools.tools.video_downloader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivityInstaDownloaderHomeBinding
import com.dev4life.watools.databinding.DialogServerDownBinding
import com.dev4life.watools.tools.BaseActivity
import com.dev4life.watools.tools.apis.InstaModel
import com.dev4life.watools.tools.apis.POST_TYPE
import com.dev4life.watools.tools.apis.RestApiClient
import com.dev4life.watools.tools.downloader.BasicImageDownloader
import com.dev4life.watools.tools.mycreation.MyCreationToolsActivity
import com.dev4life.watools.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstaDownloaderHomeActivity : BaseActivity() {

    val binding by lazy { ActivityInstaDownloaderHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {

            if (NetworkState.isOnline()) {
//                AdsUtils.loadBanner(
//                    this@InstaDownloaderHomeActivity, bannerContainer,
//                    getString(R.string.banner_id_details)
//                )

                AdsUtils.loadNative(
                    this@InstaDownloaderHomeActivity,
                    getString(R.string.admob_native_id),
                    adFrame
                )
            }

            imgBack.setOnClickListener {
                onBackPressed()
            }

            imgDownloads.setOnClickListener {
                startActivity(
                    Intent(
                        this@InstaDownloaderHomeActivity,
                        MyCreationToolsActivity::class.java
                    ).apply {
                        putExtra(MyCreationToolsActivity.CREATION_TYPE, "insta_downloader")
                    }
                )
            }

            btnPaste.setOnClickListener {
                etText.setText(getClipboardItemsSpecific(SMType.INSTA))
            }

            btnDownload.setOnClickListener {
                if (etText.text.isNotEmpty()) {
                    if (NetworkState.isOnline()) {
                        AdsUtils.loadInterstitialAd(this@InstaDownloaderHomeActivity,
                            getString(R.string.interstitial_id),
                            object : AdsUtils.Companion.FullScreenCallback() {
                                override fun continueExecution() {
                                    startDownload(etText.text.trim().toString())
                                }
                            })
                    } else Toast.makeText(
                        this@InstaDownloaderHomeActivity,
                        "Please check your internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@InstaDownloaderHomeActivity,
                        "Please enter link",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

//            imgClear.setOnClickListener { etText.text.clear() }
        }
    }

    private fun startDownload(url: String) {
        Log.e("TAG", "startDownload: $url")
        MyProgressDialog.showDialog(this, "Please wait...", false)
        val service = RestApiClient.getInstance(RestApiClient.Companion.SOCIAL_TYPE.INSTA).service

        val call: Call<InstaModel> = service.getMediaUrlInstagram(
            getString(R.string.rapid_api_key),
            getString(R.string.rapid_api_host_insta),
            url
        )

        call.enqueue(object : Callback<InstaModel> {
            override fun onResponse(call: Call<InstaModel>, response: Response<InstaModel>) {
                MyProgressDialog.dismissDialog()
                Log.e("TAG", "isShowing: ${MyProgressDialog.dialog?.isShowing}")
                if (response.isSuccessful) {
                    val instaModel = response.body()

                    instaModel?.let { model ->
                        if (model.getPostType() == POST_TYPE.PHOTO) {
                            BasicImageDownloader(this@InstaDownloaderHomeActivity)
                                .saveImageToExternalInsta(
                                    instaModel.media
                                ) {
                                    Toast.makeText(
                                        this@InstaDownloaderHomeActivity,
                                        "Image Downloaded.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            BasicImageDownloader(this@InstaDownloaderHomeActivity)
                                .saveVideoToExternalInsta(
                                    instaModel.media
                                ) {
                                    Toast.makeText(
                                        this@InstaDownloaderHomeActivity,
                                        "Video Downloaded.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    } ?: run {
                        MyProgressDialog.dismissDialog()
                        showErrorDialog()
                    }
                } else {
                    Log.e("TAG", "onResponseError: ${response.errorBody()}")
                    MyProgressDialog.dismissDialog()
                    showErrorDialog()
                }
            }

            override fun onFailure(call: Call<InstaModel>, t: Throwable) {
                MyProgressDialog.dismissDialog()
                showErrorDialog()
            }
        })
    }

    private fun showErrorDialog() {
        val dialogServerDownBinding = DialogServerDownBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this, R.style.RoundedCornersDialog80).setCancelable(true)
            .setView(dialogServerDownBinding.root)

        val alertDialog = builder.create()
        alertDialog.show()

        dialogServerDownBinding.run {
            btnOk.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }

    fun getClipboardItemsSpecific(type: SMType): String {
        val clipboardItems = getClipBoardItems(this)

        if (clipboardItems.isNotEmpty()) {
            val fbList = mutableListOf<String>()
            val instaList = mutableListOf<String>()
            for (i in clipboardItems.indices) {
                if (clipboardItems[i].contains("www.facebook.com")) {
                    fbList.add(clipboardItems[i])
                }
            }
            for (i in clipboardItems.indices) {
                if (clipboardItems[i].contains("www.instagram.com")) {
                    instaList.add(clipboardItems[i])
                }
            }

            when (type) {
                SMType.INSTA -> {
                    if (instaList.isNotEmpty()) {
                        return instaList[0]
                    }
                    return ""
                }
                SMType.FACEBOOK -> {
                    if (fbList.isNotEmpty()) {
                        return fbList[0]
                    }
                    return ""
                }
                else -> {
                    return if (clipboardItems[0].contains("http"))
                        clipboardItems[0]
                    else ""
                }
            }
        }
        return ""
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        AdsUtils.destroyBanner()
        super.onDestroy()
    }
}