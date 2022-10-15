package com.dev4life.watools.ui.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dev4life.watools.R
import com.dev4life.watools.collage_maker.ui.activities.CollageMakerHomeActivity
import com.dev4life.watools.databinding.FragmentToolsBinding
import com.dev4life.watools.tools.age_calc.AgeCalculatorActivity
import com.dev4life.watools.tools.cartoonify.CartoonifyHomeActivity
import com.dev4life.watools.tools.cartoonify.SketchifyHomeActivity
import com.dev4life.watools.tools.cleaner.CleanerActivity
import com.dev4life.watools.tools.compress.PhotoCmpHomeActivity
import com.dev4life.watools.tools.funny.FunnyVideosActivity
import com.dev4life.watools.tools.insta_grid.InstaGridActivity
import com.dev4life.watools.tools.mycreation.MyCreationToolsActivity
import com.dev4life.watools.tools.photo_filters.PhotoFilterHomeActivity
import com.dev4life.watools.tools.photo_filters.deform.PhotoWarpHomeActivity
import com.dev4life.watools.tools.photoeditor.PicEditorHomeActivity
import com.dev4life.watools.tools.video_downloader.FBDownloaderHomeActivity
import com.dev4life.watools.tools.video_downloader.InstaDownloaderHomeActivity
import com.dev4life.watools.tools.video_player.DemoUtil
import com.dev4life.watools.tools.video_player.VideoPlayerActivity
import com.dev4life.watools.tools.wallpapers.WallpapersActivity
import com.dev4life.watools.utils.AdsUtils
import com.dev4life.watools.utils.DeviceUtils
import com.dev4life.watools.utils.NetworkState
import com.dev4life.watools.utils.getRealPathFromUri
import gun0912.tedimagepicker.builder.TedImagePicker

class ToolsFragment : BaseFragment<FragmentToolsBinding>() {
    override fun getLayout(): FragmentToolsBinding {
        return FragmentToolsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            llInstagram.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, InstaDownloaderHomeActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, InstaDownloaderHomeActivity::class.java))
                }
            }

            llFacebook.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, FBDownloaderHomeActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, FBDownloaderHomeActivity::class.java))
                }
            }

//            llWhatsappSide.setOnClickListener {
//                AdsUtils.clicksCountTools++
//                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
//                    AdsUtils.clicksCountTools = 0
//                    AdsUtils.loadInterstitialAd(
//                        requireActivity(),
//                        ctx.getString(R.string.interstitial_id),
//                        object : AdsUtils.Companion.FullScreenCallback() {
//                            override fun continueExecution() {
//                                startActivity(Intent(ctx, WAStatusActivity::class.java))
//                            }
//                        })
//                } else {
//                    startActivity(Intent(ctx, WAStatusActivity::class.java))
//                }
//            }

            llWallpaper.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(
                                    Intent(ctx, WallpapersActivity::class.java)
                                        .putExtra("walpType", "wallpapers")
                                )
                            }
                        })
                } else {
                    startActivity(
                        Intent(ctx, WallpapersActivity::class.java)
                            .putExtra("walpType", "wallpapers")
                    )
                }
            }

            llStatusMaker.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(
                                    Intent(ctx, WallpapersActivity::class.java)
                                        .putExtra("walpType", "status")
                                )
                            }
                        })
                } else {
                    startActivity(
                        Intent(ctx, WallpapersActivity::class.java)
                            .putExtra("walpType", "status")
                    )
                }
            }

            llPhotoEditor.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(
                                    Intent(
                                        ctx,
                                        PicEditorHomeActivity::class.java
                                    )
                                )
                            }
                        })
                } else {
                    startActivity(
                        Intent(
                            ctx,
                            PicEditorHomeActivity::class.java
                        )
                    )
                }
            }

            llFunny.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, FunnyVideosActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, FunnyVideosActivity::class.java))
                }
            }

            llAgeCalc.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, AgeCalculatorActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, AgeCalculatorActivity::class.java))
                }
            }

            llInstaGrid.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, InstaGridActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, InstaGridActivity::class.java))
                }
            }

            llPhotoCompress.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, PhotoCmpHomeActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, PhotoCmpHomeActivity::class.java))
                }
            }

            llCleaner.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, CleanerActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, CleanerActivity::class.java))
                }
            }

            llCollageMaker.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                launchCollage()
                            }
                        })
                } else {
                    launchCollage()
                }
            }

            llCartoonify.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, CartoonifyHomeActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, CartoonifyHomeActivity::class.java))
                }
            }

            llSketchify.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, SketchifyHomeActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, SketchifyHomeActivity::class.java))
                }
            }

            llPhotoFilter.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, PhotoFilterHomeActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, PhotoFilterHomeActivity::class.java))
                }
            }

            llPhotoWarp.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(Intent(ctx, PhotoWarpHomeActivity::class.java))
                            }
                        })
                } else {
                    startActivity(Intent(ctx, PhotoWarpHomeActivity::class.java))
                }
            }

            llDownloads.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                startActivity(
                                    Intent(
                                        ctx,
                                        MyCreationToolsActivity::class.java
                                    ).putExtra(MyCreationToolsActivity.CREATION_TYPE, "all")
                                )
                            }
                        })
                } else {
                    startActivity(
                        Intent(
                            ctx,
                            MyCreationToolsActivity::class.java
                        ).putExtra(MyCreationToolsActivity.CREATION_TYPE, "all")
                    )
                }
            }

            llVideoPlayer.setOnClickListener {
                AdsUtils.clicksCountTools++
                if (NetworkState.isOnline() && AdsUtils.clicksCountTools == 2) {
                    AdsUtils.clicksCountTools = 0
                    AdsUtils.loadInterstitialAd(
                        requireActivity(),
                        ctx.getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {
                            override fun continueExecution() {
                                TedImagePicker.with(ctx)
                                    .dropDownAlbum()
                                    .video()
                                    .showVideoDuration(true)
                                    .imageCountTextFormat("%s videos")
                                    .start { uri: Uri? ->
                                        val videoPath: String = getRealPathFromUri(ctx, uri).toString()
                                        val intent =
                                            Intent(context, VideoPlayerActivity::class.java)
                                        intent.putExtra("selectedvideo", videoPath)
                                        intent.putExtra(
                                            DemoUtil.VID_ORIENTATION,
                                            DeviceUtils.rotateScreen(context, uri)
                                        )
                                        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                        startActivity(intent)
                                    }
                            }
                        })
                } else {
                    TedImagePicker.with(ctx)
                        .dropDownAlbum()
                        .video()
                        .showVideoDuration(true)
                        .imageCountTextFormat("%s videos")
                        .start { uri: Uri? ->
                            val videoPath: String = getRealPathFromUri(ctx, uri).toString()
                            val intent =
                                Intent(context, VideoPlayerActivity::class.java)
                            intent.putExtra("selectedvideo", videoPath)
                            intent.putExtra(
                                DemoUtil.VID_ORIENTATION,
                                DeviceUtils.rotateScreen(context, uri)
                            )
                            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            startActivity(intent)
                        }
                }
            }
        }
    }

    private fun launchCollage() {
        startActivity(
            Intent(
                context,
                CollageMakerHomeActivity::class.java
            ).putExtra(MyCreationToolsActivity.CREATION_TYPE, "collage_maker")
        )
    }

    override fun onBackPressed() {

    }
}