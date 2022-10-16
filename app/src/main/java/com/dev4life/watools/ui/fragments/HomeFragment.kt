package com.dev4life.watools.ui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dev4life.watools.R
import com.dev4life.watools.databinding.FragmentHomeBinding
import com.dev4life.watools.ui.activities.WAToolsActivity


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getLayout(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

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

            viewPagerHomeStatus.isUserInputEnabled = false
            viewPagerHomeStatus.adapter = FragmentsAdapter(requireActivity())

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

//        binding.run {
//            btnSend.setOnClickListener {
//                if (edtNumber.text.toString().trim().isNotEmpty()
//                    && edtMessage.text.toString().trim().isNotEmpty()
//                ) {
//                    var toNumber = edtNumber.text.toString() // contains spaces.
//
//                    toNumber = toNumber.replace("+", "").replace(" ", "")
//
//                    val sendIntent = Intent(Intent.ACTION_MAIN)
//                    sendIntent.putExtra("jid", "$toNumber@s.whatsapp.net")
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, edtMessage.text.toString())
//                    sendIntent.action = Intent.ACTION_SEND
//                    sendIntent.setPackage("com.whatsapp")
//                    sendIntent.type = "text/plain"
//                    startActivity(sendIntent)
//                } else {
//                    Toast.makeText(ctx, "Both fields are mandatory.", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            btnSendEmpy.setOnClickListener {
//                shareBlankWhatsApp()
//            }
//        }
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

    fun shareBlankWhatsApp() {
        try {
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.putExtra(Intent.EXTRA_TEXT, ctx.getString(R.string.blank_massage))
            waIntent.action = Intent.ACTION_SEND
            waIntent.setPackage("com.whatsapp")
            waIntent.type = "text/plain"
            startActivity(waIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(ctx, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
        }
    }

    inner class FragmentsAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return HomeStatusFragment.newInstance()
                }
                1 -> {
                    return ToolsFragment.newInstance()
                }
            }
            return HomeStatusFragment.newInstance()
        }
    }

    override fun onBackPressed() {
    }
}