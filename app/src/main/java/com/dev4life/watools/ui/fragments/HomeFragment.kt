package com.dev4life.watools.ui.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dev4life.watools.R
import com.dev4life.watools.databinding.FragmentHomeBinding


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
            btnSend.setOnClickListener {
                if (edtNumber.text.toString().trim().isNotEmpty()
                    && edtMessage.text.toString().trim().isNotEmpty()
                ) {
                    var toNumber = edtNumber.text.toString() // contains spaces.

                    toNumber = toNumber.replace("+", "").replace(" ", "")

                    val sendIntent = Intent(Intent.ACTION_MAIN)
                    sendIntent.putExtra("jid", "$toNumber@s.whatsapp.net")
                    sendIntent.putExtra(Intent.EXTRA_TEXT, edtMessage.text.toString())
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.setPackage("com.whatsapp")
                    sendIntent.type = "text/plain"
                    startActivity(sendIntent)
                } else {
                    Toast.makeText(ctx, "Both fields are mandatory.", Toast.LENGTH_SHORT).show()
                }
            }

            btnSendEmpy.setOnClickListener {
                shareBlankWhatsApp()
            }
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

    override fun onBackPressed() {
    }
}