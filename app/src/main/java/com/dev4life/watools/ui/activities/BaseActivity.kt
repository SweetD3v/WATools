package com.dev4life.watools.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev4life.watools.R
import com.dev4life.watools.utils.setLightStatusBarColor

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLightStatusBarColor(this, window, R.color.white)
    }
}