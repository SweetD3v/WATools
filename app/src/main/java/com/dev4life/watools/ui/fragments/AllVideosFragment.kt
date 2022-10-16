package com.dev4life.watools.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.dev4life.watools.adapter.AllMediaAdapter
import com.dev4life.watools.adapters.WAMediaAdapter
import com.dev4life.watools.databinding.FragmentWaimagesBinding
import com.dev4life.watools.models.Media
import com.dev4life.watools.utils.dpToPx
import com.dev4life.watools.utils.getMedia
import com.dev4life.watools.widgets.MarginItemDecoration


class AllVideosFragment : BaseFragment<FragmentWaimagesBinding>() {
    override fun getLayout(): FragmentWaimagesBinding {
        return FragmentWaimagesBinding.inflate(layoutInflater)
    }

    var imagesList = mutableListOf<Media>()

    companion object {
        open fun newInstance(): AllVideosFragment {
            return AllVideosFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadVideos()
    }

    private fun loadVideos() {
        binding.run {
            rvWAImages.layoutManager = GridLayoutManager(ctx, 2)
            rvWAImages.addItemDecoration(MarginItemDecoration(dpToPx(8)))

            getMedia(ctx) { list ->
                if (imagesList.size != list.size) {
                    getMedia(ctx) { list ->
                        if (imagesList.size != list.size) {
                            for (media in list) {
                                if (media.isVideo) {
                                    imagesList.add(media)
                                }
                            }
                            val allMediaAdapter = AllMediaAdapter(ctx, imagesList, binding.rlMain)
                            rvWAImages.adapter = allMediaAdapter
                        }
                    }
                    val waMediaAdapter = WAMediaAdapter(ctx, imagesList, binding.rlMain)
                    rvWAImages.adapter = waMediaAdapter
                }
            }
        }
    }

    override fun onBackPressed() {
    }
}