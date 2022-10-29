package com.dev4life.watools.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dev4life.watools.R
import com.dev4life.watools.databinding.ActivitySendEmptyBinding

class EmptySendActivity : BaseActivity() {

    val binding by lazy { ActivitySendEmptyBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            btnSend.setOnClickListener {
                if (edtLines.text.toString().trim().isNotEmpty()
                ) {
                    var toCode = ccpPhone.selectedCountryCode() // contains spaces.

                    toCode = toCode.replace("+", "").replace(" ", "")

                    val phoneNo = toCode + edtNumber.text.toString()

//                    val sendIntent = Intent(Intent.ACTION_MAIN)
//                    sendIntent.putExtra("jid", "$phoneNo@s.whatsapp.net")
//                    sendIntent.putExtra(Intent.EXTRA_TEXT, edtLines.text.toString())
//                    sendIntent.action = Intent.ACTION_SEND
//                    sendIntent.setPackage("com.whatsapp")
//                    sendIntent.type = "text/plain"
//                    startActivity(sendIntent)

                    val lines = edtLines.text.toString().toInt()

                    val sb = StringBuilder("")
                    for (i in 0 until lines) {
                        sb.append(getString(R.string.blank_massage))
                        if (i != (lines - 1)) {
                            sb.append("\n")
                        }
                    }

                    shareBlankWhatsApp(sb.toString())
                } else {
                    Toast.makeText(
                        this@EmptySendActivity,
                        "Both fields are mandatory.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun shareBlankWhatsApp(message: String) {
        try {
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.putExtra(Intent.EXTRA_TEXT, message)
            waIntent.action = Intent.ACTION_SEND
            waIntent.setPackage("com.whatsapp")
            waIntent.type = "text/plain"
            startActivity(waIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
        }
    }
}