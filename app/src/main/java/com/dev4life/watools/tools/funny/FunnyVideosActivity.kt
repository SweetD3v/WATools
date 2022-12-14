package com.dev4life.watools.tools.funny

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivityFunnyVideosBinding
import com.dev4life.watools.tools.downloader.BasicImageDownloader
import com.dev4life.watools.utils.AdsUtils
import com.dev4life.watools.utils.NetworkState
import com.dev4life.watools.utils.setDarkStatusBarColor

class FunnyVideosActivity : AppCompatActivity() {
    val binding by lazy { ActivityFunnyVideosBinding.inflate(layoutInflater) }

    var downloadUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDarkStatusBarColor(this, R.color.black)
        setContentView(binding.root)

        loadVideos()

        binding.run {
            if (NetworkState.isOnline())
                AdsUtils.loadBanner(
                    this@FunnyVideosActivity, getString(R.string.banner_id_details),
                    bannerContainer
                )

            imgBack.setOnClickListener { onBackPressed() }
        }
    }

    private fun loadVideos() {
        binding.run {
            webView.isSaveEnabled = true
            webView.setNetworkAvailable(true)
            webView.settings.apply {
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = false
                loadsImagesAutomatically = true
                domStorageEnabled = true

                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                domStorageEnabled = true
                mediaPlaybackRequiresUserGesture = false
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false

                webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            }
            webView.webViewClient = WVClient()
            webView.webChromeClient = ChromeClient()
            webView.loadUrl("https://myvideo.fun")

            fabDownload.setOnClickListener {
                startDownload(downloadUrl)
            }
        }
    }

    private fun startDownload(downloadUrl: String?) {
        downloadUrl?.let { url ->
            Log.e("TAG", "startDownload: $url")
            BasicImageDownloader(this@FunnyVideosActivity)
                .saveVideoToExternalFunny(
                    url
                ) {
                    Toast.makeText(
                        this@FunnyVideosActivity,
                        "Video Downloaded.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    inner class ChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)

            if (view?.url.toString() != "https://myvideo.fun/") {
                downloadUrl = view?.url.toString()
            }
            Log.e("TAG", "onProgressChanged: ${view?.url}")
        }
    }

    inner class WVClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            Log.e("TAG", "shouldOverrideUrlLoading: ${request?.url}")
            return true
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(view, handler, error)
            Log.e("TAG", "onReceivedSslError: ${error.toString()}")
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e("TAG", "onReceivedError: ${error?.description}")
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}