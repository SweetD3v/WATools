package com.dev4life.watools.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dev4life.watools.databinding.ActivityMainBinding
import com.dev4life.watools.speedmeter.SpeedMeterService
import com.google.android.material.snackbar.Snackbar

class MainActivity : BaseActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val speedMeterIntent =
            Intent(this@MainActivity, SpeedMeterService::class.java)
        startService(speedMeterIntent)
    }

    var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Snackbar.make(binding.root, "Press BACK again to exit", Snackbar.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}