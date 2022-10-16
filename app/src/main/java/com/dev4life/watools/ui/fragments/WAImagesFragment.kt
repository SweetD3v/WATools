package com.dev4life.watools.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev4life.watools.R
import com.dev4life.watools.adapters.WAMediaAdapter
import com.dev4life.watools.databinding.FragmentWaimagesBinding
import com.dev4life.watools.models.Media
import com.dev4life.watools.utils.addOuterGridSpacing
import com.dev4life.watools.utils.getMediaWA

class WAImagesFragment : BaseFragment<FragmentWaimagesBinding>() {
    override fun getLayout(): FragmentWaimagesBinding {
        return FragmentWaimagesBinding.inflate(layoutInflater)
    }

    var imagesList: MutableList<Media> = mutableListOf()
    var decorationAdded: Boolean? = false

    companion object {
        open fun newInstance(): WAImagesFragment {
            return WAImagesFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            rvWAImages.layoutManager = GridLayoutManager(ctx, 2)
//            rvWAImages.addItemDecoration(MarginItemDecoration(dpToPx(8)))
            val albumGridSpacing = resources.getDimension(R.dimen.rv_margin)
            if (decorationAdded == false) {
                decorationAdded = true
                rvWAImages.addOuterGridSpacing((albumGridSpacing).toInt())
                rvWAImages.addItemDecoration(GridMarginDecoration(albumGridSpacing.toInt()))
            }
        }
    }

    internal class GridMarginDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect, view: View,
            parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.left = space / 2
            outRect.top = space / 2
            outRect.right = space / 2
            outRect.bottom = space / 2
        }
    }


    override fun onResume() {
        super.onResume()

//        if (allPermissionsGranted()) {
//            onPermissionGranted()
//        } else {
//            permissionRequest.launch(permissions.toTypedArray())
//        }

        loadImages()
    }

    override fun onPermissionGranted() {
//        loadImages()
    }

    private fun loadImages() {
        Log.e("TAG", "loadImages: ")
        binding.apply {
            val imageListNew = mutableListOf<Media>()
            getMediaWA(ctx) { list ->
                for (media in list) {
//                    if (!media.isVideo and !media.uri.toString().contains(".nomedia", true)
//                    ) {
                    imageListNew.add(media)
//                    }
                }
                Log.e("TAG", "loadImagesNew: ${imageListNew.size}")
                Log.e("TAG", "loadImages: ${imagesList.size}")
                if (imageListNew.size != imagesList.size) {
                    imagesList = imageListNew
                    val waMediaAdapter = WAMediaAdapter(ctx, imagesList, binding.rlMain)
                    binding.rvWAImages.adapter = waMediaAdapter
                    waMediaAdapter.notifyItemRangeChanged(0, imagesList.size)
                }
            }
        }
    }

    override fun onBackPressed() {
    }
}