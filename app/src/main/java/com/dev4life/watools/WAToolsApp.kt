package com.dev4life.watools

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.util.Util

class WAToolsApp : MultiDexApplication() {
    companion object {
        lateinit var mInstance: WAToolsApp

        @Synchronized
        fun getInstance(): WAToolsApp {
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        Fresco.initialize(this)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun buildHttpDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter?): HttpDataSource.Factory {
        return DefaultHttpDataSourceFactory(Util.getUserAgent(this, "WAToolsApp"), bandwidthMeter)
    }
}