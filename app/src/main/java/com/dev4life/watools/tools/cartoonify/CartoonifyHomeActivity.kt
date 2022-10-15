package com.dev4life.watools.tools.cartoonify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivityCartoonifyHomeBinding
import com.dev4life.watools.tools.BaseActivity
import com.dev4life.watools.tools.mycreation.MyCreationToolsActivity
import com.dev4life.watools.utils.AdsUtils
import com.dev4life.watools.utils.NetworkState
import gun0912.tedimagepicker.builder.TedImagePicker

class CartoonifyHomeActivity : BaseActivity() {

    val binding by lazy { ActivityCartoonifyHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {

            if (NetworkState.isOnline()) {
//                AdsUtils.loadBanner(
//                    this@CartoonifyHomeActivity, bannerContainer,
//                    getString(R.string.banner_id_details)
//                )

                AdsUtils.loadNative(
                    this@CartoonifyHomeActivity,
                    getString(R.string.admob_native_id),
                    adFrame
                )
            }

            imgBack.setOnClickListener {
                onBackPressed()
            }

            llCartoonify.setOnClickListener {
                TedImagePicker.with(this@CartoonifyHomeActivity)
                    .dropDownAlbum()
                    .imageCountTextFormat("%s images")
                    .start { uri: Uri ->
                        val intent = Intent(
                            this@CartoonifyHomeActivity,
                            CartoonActivity::class.java
                        )
                        intent.putExtra(CartoonActivity.SELECTED_PHOTO, uri.toString())
                        startActivity(intent)
                    }
            }

            llMyCreation.setOnClickListener {
                startActivity(
                    Intent(
                        this@CartoonifyHomeActivity,
                        MyCreationToolsActivity::class.java
                    ).apply {
                        putExtra(MyCreationToolsActivity.CREATION_TYPE, "cartoonify")
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