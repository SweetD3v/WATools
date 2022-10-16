package com.dev4life.watools.ui.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.Activity.STORAGE_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.dev4life.watools.databinding.FragmentHomeStatusBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeStatusFragment : BaseFragment<FragmentHomeStatusBinding>() {
    override fun getLayout(): FragmentHomeStatusBinding {
        return FragmentHomeStatusBinding.inflate(layoutInflater)
    }

    companion object {
        open fun newInstance(): HomeStatusFragment {
            return HomeStatusFragment()
        }
    }

    private val PERMISSIONS = mutableListOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    ).apply {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
    private val tabTitles = arrayOf("Status", "Downloads")

    val imgFragment = WAImagesFragment.newInstance()
    val savedFragment = WADownloadsFragment.newInstance()

    val permissionsLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            var granted = true
            if (result != null) {
                for (b in result.values) {
                    if (!b) {
                        granted = false
                        break
                    }
                }
            } else granted = false

        }

    val statusFileResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent = result.data!!
                Log.d("HEY: ", data.data.toString())
                ctx.contentResolver.takePersistableUriPermission(
                    data.data!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        Log.e("TAG", "onCreate: " + isAllPermissionsGranted())
        if (!isAllPermissionsGranted() || ctx.contentResolver.persistedUriPermissions.size <= 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R
                && ctx.contentResolver.persistedUriPermissions.size <= 0
            ) {
                openDocTreeStatus()
            } else {
                if (!isAllPermissionsGranted())
                    permissionsLauncher.launch(PERMISSIONS.toTypedArray())
                else setupViewPager()
            }
        } else {
            setupViewPager()
        }
    }

    private fun setupViewPager() {
        binding.apply {

            try {
                viewPagerStatus.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                viewPagerStatus.adapter = FragmentsAdapter(requireActivity())

                TabLayoutMediator(tabLayout, viewPagerStatus) { tab, position ->
                    tab.text = tabTitles[position]
                }.attach()
            } catch (e: Exception) {
            }
        }
    }

    fun isAllPermissionsGranted() = PERMISSIONS.all {
        ContextCompat.checkSelfPermission(ctx, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun arePermissionDenied(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return ctx.contentResolver.persistedUriPermissions.size <= 0
        }
        for (str in PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(
                    ctx,
                    str
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return true
            }
        }
        return false
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun openDocTreeStatus() {
        Log.e("TAG", "requestPermissionQ: ")
        val createOpenDocumentTreeIntent =
            (ctx.getSystemService(STORAGE_SERVICE) as StorageManager).primaryStorageVolume.createOpenDocumentTreeIntent()
        val replace: String =
            (createOpenDocumentTreeIntent.getParcelableExtra<Parcelable>(DocumentsContract.EXTRA_INITIAL_URI) as Uri?).toString()
                .replace("/root/", "/document/")
        val parse: Uri =
            Uri.parse("$replace%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses")
        Log.d("URI", parse.toString())
        createOpenDocumentTreeIntent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, parse)
        statusFileResultLauncher.launch(createOpenDocumentTreeIntent)
    }

    inner class FragmentsAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return imgFragment
                }
                1 -> {
                    return savedFragment
                }
            }
            return imgFragment
        }
    }

    override fun onBackPressed() {

    }
}