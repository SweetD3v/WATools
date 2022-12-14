package com.dev4life.watools.tools.photoeditor

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivityPicEditorHomeBinding
import com.dev4life.watools.tools.mycreation.MyCreationToolsActivity
import com.dev4life.watools.ui.activities.BaseActivity
import com.dev4life.watools.ui.fragments.HomeFragment
import com.dev4life.watools.utils.AdsUtils
import com.dev4life.watools.utils.NetworkState
import gun0912.tedimagepicker.builder.TedImagePicker

class PicEditorHomeActivity : BaseActivity() {
    val binding by lazy { ActivityPicEditorHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {

            if (NetworkState.isOnline()) {
//                AdsUtils.loadBanner(
//                    this@PicEditorHomeActivity, bannerContainer,
//                    getString(R.string.banner_id_details)
//                )

                AdsUtils.loadNative(
                    this@PicEditorHomeActivity,
                    getString(R.string.admob_native_id),
                    adFrame
                )
            }

            imgBack.setOnClickListener {
                onBackPressed()
            }

            llEdit.setOnClickListener {
                TedImagePicker.with(this@PicEditorHomeActivity)
                    .dropDownAlbum()
                    .imageCountTextFormat("%s images")
                    .start { uri: Uri ->
                        Log.e("TAG", "onCreatePic: $uri")
                        val intent = Intent(
                            this@PicEditorHomeActivity,
                            PicEditActivity::class.java
                        )
                        intent.putExtra(HomeFragment.KEY_SELECTED_PHOTOS, uri.toString())
                        startActivity(intent)
                    }
            }

            llMyCreation.setOnClickListener {
                startActivity(
                    Intent(
                        this@PicEditorHomeActivity,
                        MyCreationToolsActivity::class.java
                    ).putExtra(MyCreationToolsActivity.CREATION_TYPE, "photo_editor")
                )
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        AdsUtils.destroyBanner()
        super.onDestroy()
    }
}