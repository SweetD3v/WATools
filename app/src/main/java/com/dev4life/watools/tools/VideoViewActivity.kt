package com.dev4life.watools.tools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dev4life.watools.databinding.ActivityVideoviewBinding

class VideoViewActivity : AppCompatActivity() {
    private val binding by lazy { ActivityVideoviewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            back.setOnClickListener { onBackPressed() }

            videoView.setVideoURI(intent.getStringExtra("path").toString().toUri())
            videoView.setMediaController(mediaController)
            videoView.start()
        }
    }

    override fun onBackPressed() {
        binding.apply {
            if (videoView.isPlaying) {
                videoView.pause()
            }
        }
        finish()
    }
}