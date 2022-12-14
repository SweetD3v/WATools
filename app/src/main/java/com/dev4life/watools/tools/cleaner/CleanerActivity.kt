package com.dev4life.watools.tools.cleaner

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivityCacheCleanerBinding
import com.dev4life.watools.tools.BaseActivity
import com.dev4life.watools.utils.*
import com.dev4life.watools.utils.AdsUtils.Companion.loadInterstitialAd

class CleanerActivity : BaseActivity() {
    val binding by lazy { ActivityCacheCleanerBinding.inflate(layoutInflater) }

    var handler: Handler? = Handler(Looper.getMainLooper())
    var handlerReverse: Handler? = Handler(Looper.getMainLooper())
    var cachePercentageReverse: Int = 100
    var cachePercentage: Int = 0
    var showedAd: Boolean = false

    val runnableReverse = object : Runnable {
        override fun run() {
            cachePercentageReverse -= 2
            binding.txtTotalPercentage.text = "$cachePercentageReverse %"
            binding.btnCleanCache.text = "Cleaning..."
            handler?.postDelayed(this, 50)

            if (cachePercentageReverse == 0) {
                handlerReverse?.removeCallbacks(this)
                handlerReverse = null

                binding.run {
                    txtTotalCacheSize.animate().scaleX(0f).scaleY(0f).apply {
                        duration = 500
                        setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(p0: Animator) {
                            }

                            override fun onAnimationEnd(p0: Animator) {
                                txtTotalCacheSize.text = "Cache cleaned"
                            }

                            override fun onAnimationCancel(p0: Animator) {
                            }

                            override fun onAnimationRepeat(p0: Animator) {
                            }
                        })
                    }
                    txtTotalPercentage.animate().scaleX(0f).scaleY(0f).apply {
                        duration = 500
                        setListener(object : Animator.AnimatorListener {
                            override fun onAnimationStart(p0: Animator) {
                            }

                            override fun onAnimationEnd(p0: Animator) {
                                txtTotalPercentage.text = "0 B"
                            }

                            override fun onAnimationCancel(p0: Animator) {
                            }

                            override fun onAnimationRepeat(p0: Animator) {
                            }
                        })
                    }

                    Handler(Looper.getMainLooper())
                        .postDelayed({
                            txtTotalPercentage.animate().scaleX(1f).scaleY(1f).apply {
                                duration = 500
                            }
                            txtTotalCacheSize.animate().scaleX(1f).scaleY(1f).apply {
                                duration = 500
                            }
                            btnCleanCache.text = "Done"
                            btnCleanCache.isEnabled = true
                        }, 500)
                }
            }

            handlerReverse?.postDelayed(this, 50)
        }
    }

    val runnable = object : Runnable {
        override fun run() {
            cachePercentage += 1
            binding.txtTotalPercentage.text = "$cachePercentage %"
            binding.btnCleanCache.text = "Optimizing..."
            handler?.postDelayed(this, 50)

            if (cachePercentage == 100) {
                handler?.removeCallbacks(this)
                handler = null
                cachePercentage = 0

                binding.run {
                    object : AsyncTaskRunner<Void?, String>(this@CleanerActivity) {
                        override fun doInBackground(params: Void?): String {
                            return cacheDir.getProperSize(true).formatSize()
                        }

                        override fun onPostExecute(result: String?) {
                            super.onPostExecute(result)
                            result?.let { size ->

                                txtTotalPercentage.animate().scaleX(0f).scaleY(0f).apply {
                                    duration = 500
                                    setListener(object : Animator.AnimatorListener {
                                        override fun onAnimationStart(p0: Animator) {
                                        }

                                        override fun onAnimationEnd(p0: Animator) {
                                            txtTotalPercentage.text = size
                                        }

                                        override fun onAnimationCancel(p0: Animator) {
                                        }

                                        override fun onAnimationRepeat(p0: Animator) {
                                        }
                                    })
                                }
                                txtTotalCacheSize.animate().scaleX(0f).scaleY(0f).apply {
                                    duration = 500
                                    setListener(object : Animator.AnimatorListener {
                                        override fun onAnimationStart(p0: Animator) {
                                        }

                                        override fun onAnimationEnd(p0: Animator) {
                                            txtTotalCacheSize.text = "Cache size"
                                            btnCleanCache.text = "Clean"
                                            btnCleanCache.isEnabled = true
                                        }

                                        override fun onAnimationCancel(p0: Animator) {
                                        }

                                        override fun onAnimationRepeat(p0: Animator) {
                                        }
                                    })
                                }

                                Handler(Looper.getMainLooper())
                                    .postDelayed({
                                        txtTotalPercentage.animate().scaleX(1f).scaleY(1f).apply {
                                            duration = 500
                                        }
                                        txtTotalCacheSize.animate().scaleX(1f).scaleY(1f).apply {
                                            duration = 500
                                        }

                                    }, 500)
                            }
                        }
                    }.execute(null, false)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {

            if (NetworkState.isOnline()) {
                AdsUtils.loadNativeSmall(
                    this@CleanerActivity, getString(R.string.admob_native_id),
                    adFrame
                )
            }

            imgBack.setOnClickListener {
                onBackPressed()
            }

            btnCleanCache.setOnClickListener {
                if (btnCleanCache.text.equals("Done")) {
                    loadInterstitialAd(
                        this@CleanerActivity,
                        getString(R.string.interstitial_id),
                        object : AdsUtils.Companion.FullScreenCallback() {

                            override fun onAdFailed() {
                                showedAd = false
                            }

                            override fun onAdDismissed() {
                                showedAd = true
                            }

                            override fun onAdFailedToShow() {
                                showedAd = false
                            }

                            override fun onAdShowed() {
                                showedAd = true
                            }

                            override fun continueExecution() {
                                onBackPressed()
                            }
                        })
                } else {
                    object : AsyncTaskRunner<Void?, String>(this@CleanerActivity) {
                        override fun doInBackground(params: Void?): String {
                            cacheDir.deleteRecursively()
                            return "Cleaning..."
                        }

                        override fun onPostExecute(result: String?) {
                            super.onPostExecute(result)
                            result?.let { size ->
                                handlerReverse?.post(runnableReverse)
                                binding.txtTotalCacheSize.text = size
                                btnCleanCache.isEnabled = false
                            }
                        }
                    }.execute(null, false)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        handler = Handler(Looper.getMainLooper())
        handler?.post(runnable)
    }

    override fun onPause() {
        handler?.removeCallbacks(runnable)
        handler = null
        super.onPause()
    }

    override fun onBackPressed() {
        if (!showedAd) {
            loadInterstitialAd(
                this,
                getString(R.string.interstitial_id),
                object : AdsUtils.Companion.FullScreenCallback() {
                    override fun continueExecution() {
                        finish()
                    }
                })
            return
        }
        finish()
    }

    override fun onDestroy() {
        AdsUtils.destroyBanner()
        super.onDestroy()
    }
}