package com.dev4life.watools.ui.fragments

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev4life.watools.R
import com.dev4life.watools.adapters.WAMediaSavedAdapter
import com.dev4life.watools.databinding.FragmentWaimagesBinding
import com.dev4life.watools.interfaces.WATypeChangeListener
import com.dev4life.watools.models.Media
import com.dev4life.watools.utils.RootDirectoryFBDownlaoder
import com.dev4life.watools.utils.RootDirectoryWhatsappShow
import com.dev4life.watools.utils.addOuterGridSpacing
import com.dev4life.watools.utils.getMedia

class WADownloadsFragment : BaseFragment<FragmentWaimagesBinding>(), WATypeChangeListener {
    override fun getLayout(): FragmentWaimagesBinding {
        return FragmentWaimagesBinding.inflate(layoutInflater)
    }

    var imagesList: MutableList<Media> = mutableListOf()
    var decorationAdded: Boolean? = false
    var waTypeChangeListener: WATypeChangeListener? = null

    companion object {
        fun newInstance(): WADownloadsFragment {
            val fragment = WADownloadsFragment()
            fragment.waTypeChangeListener = fragment
            return fragment
        }

        fun newInstance(waTypeChangeListener: WATypeChangeListener): WADownloadsFragment {
            val fragment = WADownloadsFragment()
            fragment.waTypeChangeListener = waTypeChangeListener
            return fragment
        }

    }

    override fun onTypeChanged(type: Int) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {

//            AdsUtils.loadNative(
//                requireActivity(),
//                getString(R.string.admob_native_id),
//                adFrame
//            )

            rvWAImages.isNestedScrollingEnabled = false
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
        Log.e("TAG", "onResumeDownload: ")
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
        binding.apply {
            val imageListNew = mutableListOf<Media>()
            getMedia(ctx, RootDirectoryWhatsappShow) { list ->
                for (media in list) {
                    imageListNew.add(media)
                    Log.e("TAG", "loadImagesDS: ${media.path}")
                }
                if (imageListNew.size != imagesList.size) {
                    imagesList = imageListNew
                    val waMediaAdapter = WAMediaSavedAdapter(ctx, imagesList)
                    binding.rvWAImages.adapter = waMediaAdapter
                    waMediaAdapter.notifyItemRangeChanged(0, imagesList.size)
                }
            }
        }
    }

    override fun onBackPressed() {
        requireActivity().finish()
    }
}