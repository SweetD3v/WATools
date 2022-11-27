package com.dev4life.watools.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.dev4life.watools.R
import com.dev4life.watools.databinding.BottomSheetWaTypeBinding
import com.dev4life.watools.databinding.FragmentHomeBinding
import com.dev4life.watools.ui.activities.WAToolsActivity
import com.google.android.material.bottomsheet.BottomSheetDialog


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayout(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    var bottomSheetWAType: BottomSheetDialog? = null
    var homeStatusFragment: HomeStatusFragment = HomeStatusFragment.newInstance()
    var toolsFragment: ToolsFragment = ToolsFragment.newInstance()

    companion object {
        const val KEY_DATA_RESULT = "KEY_DATA_RESULT"
        const val KEY_SELECTED_PHOTOS = "SELECTED_PHOTOS"

        open fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {

            imgMore.setOnClickListener {
                val sheetTypeBinding = BottomSheetWaTypeBinding.inflate(layoutInflater)
                bottomSheetWAType = BottomSheetDialog(ctx, R.style.BottomSheetDialogTheme)
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

            viewPagerHomeStatus.isUserInputEnabled = false
            viewPagerHomeStatus.adapter = FragmentsAdapter(requireActivity())
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
                startActivity(Intent(ctx, WAToolsActivity::class.java))
            }
        }
    }

    private fun selectStatusFragment() {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_home_to_tools)
        }
    }

    private fun selectToolsFragment() {
        view?.let {
            Navigation.findNavController(it).navigate(R.id.action_tools_to_home_status)
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
    }
}