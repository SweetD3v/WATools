package com.dev4life.watools.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivityWatoolsBinding
import com.dev4life.watools.whatsapp_tools.wa_web.WebviewActivity

class WAToolsActivity : AppCompatActivity() {

    val binding by lazy { ActivityWatoolsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            imgBack.setOnClickListener { onBackPressed() }

            llWAWeb.setOnClickListener {
                startActivity(Intent(this@WAToolsActivity, WebviewActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}