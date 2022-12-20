package com.dev4life.watools.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev4life.watools.R
import com.dev4life.watools.databinding.BottomSheetWaTypeBinding
import com.dev4life.watools.databinding.FragmentHomeBinding
import com.dev4life.watools.ui.activities.BaseActivityBinding
import com.dev4life.watools.ui.activities.WAToolsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomeFragment : BaseActivityBinding<FragmentHomeBinding>() {
    override val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    var bottomSheetWAType: BottomSheetDialog? = null
    lateinit var homeStatusFragment: HomeStatusFragment
    lateinit var toolsFragment: ToolsFragment

    companion object {
        const val KEY_DATA_RESULT = "KEY_DATA_RESULT"
        const val KEY_SELECTED_PHOTOS = "SELECTED_PHOTOS"

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.run {

            homeStatusFragment = HomeStatusFragment.newInstance()
            toolsFragment = ToolsFragment.newInstance()

            imgMore.setOnClickListener {
                val sheetTypeBinding = BottomSheetWaTypeBinding.inflate(layoutInflater)
                bottomSheetWAType =
                    BottomSheetDialog(this@HomeFragment, R.style.BottomSheetDialogTheme)
                bottomSheetWAType?.setContentView(sheetTypeBinding.root)
                bottomSheetWAType?.show()

                sheetTypeBinding.llWA.setOnClickListener {
                    bottomSheetWAType?.dismiss()
                    homeStatusFragment.onTypeChanged(0)
                }

                sheetTypeBinding.llWAB.setOnClickListener {
                    bottomSheetWAType?.dismiss()
                    homeStatusFragment.onTypeChanged(1)
                }
            }

            viewPagerHomeStatus.isUserInputEnabled = true
            viewPagerHomeStatus.adapter = FragmentsAdapter(this@HomeFragment)
//            viewPagerHomeStatus.registerOnPageChangeCallback(object : OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                    when (position) {
//                        0 -> imgMore.visibility = View.VISIBLE
//                        1 -> imgMore.visibility = View.GONE
//                    }
//                }
//            })

            bottomNavBar.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_status -> {
                        viewPagerHomeStatus.currentItem = 0
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_tools -> {
                        viewPagerHomeStatus.currentItem = 1
                        return@setOnItemSelectedListener true
                    }
                }
                return@setOnItemSelectedListener false
            }

            bottomNavBar.selectedItemId = R.id.action_tools

            fabWATools.setOnClickListener {
                startActivity(Intent(this@HomeFragment, WAToolsActivity::class.java))
            }
        }
    }

    inner class FragmentsAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return homeStatusFragment
                }
                1 -> {
                    return toolsFragment
                }
            }
            return homeStatusFragment
        }
    }

    override fun onBackPressed() {
        finish()
    }
}