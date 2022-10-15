package com.dev4life.watools.tools.photo_filters.deform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivityPhotoWarpHomeBinding
import com.dev4life.watools.tools.BaseActivity
import com.dev4life.watools.tools.cartoonify.CartoonActivity.Companion.SELECTED_PHOTO
import com.dev4life.watools.tools.mycreation.MyCreationToolsActivity
import com.dev4life.watools.utils.AdsUtils
import com.dev4life.watools.utils.NetworkState
import gun0912.tedimagepicker.builder.TedImagePicker

class PhotoWarpHomeActivity : BaseActivity() {

    val binding by lazy { ActivityPhotoWarpHomeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {

            if (NetworkState.isOnline()) {
//                AdsUtils.loadBanner(
//                    this@PhotoWarpHomeActivity, bannerContainer,
//                    getString(R.string.banner_id_details)
//                )

                AdsUtils.loadNative(
                    this@PhotoWarpHomeActivity,
                    getString(R.string.admob_native_id),
                    adFrame
                )
            }

            llPhotoFilters.setOnClickListener {
                TedImagePicker.with(this@PhotoWarpHomeActivity)
                    .dropDownAlbum()
                    .imageCountTextFormat("%s images")
                    .start { uri: Uri? ->
                        startActivity(
                            Intent(this@PhotoWarpHomeActivity, PhotoWarpActivity::class.java)
                                .putExtra(SELECTED_PHOTO, uri.toString())
                        )
                    }
            }

            llMyCreation.setOnClickListener {
                startActivity(
                    Intent(
                        this@PhotoWarpHomeActivity,
                        MyCreationToolsActivity::class.java
                    ).apply {
                        putExtra(MyCreationToolsActivity.CREATION_TYPE, "photo_warp")
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        AdsUtils.destroyBanner()
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
    }
}