package com.dev4life.watools.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.dev4life.watools.databinding.ActivityDirectChatBinding

class DirectChatActivity : BaseActivity() {

    val binding by lazy { ActivityDirectChatBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.run {
            btnSend.setOnClickListener {
                if (edtNumber.text.toString().trim().isNotEmpty()
                    && edtMessage.text.toString().trim().isNotEmpty()
                ) {
                    var toCode = ccpPhone.selectedCountryCode() // contains spaces.

                    toCode = toCode.replace("+", "").replace(" ", "")

                    val phoneNo = toCode + edtNumber.text.toString()

                    val sendIntent = Intent(Intent.ACTION_MAIN)
                    sendIntent.putExtra("jid", "$phoneNo@s.whatsapp.net")
                    sendIntent.putExtra(Intent.EXTRA_TEXT, edtMessage.text.toString())
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.setPackage("com.whatsapp")
                    sendIntent.type = "text/plain"
                    startActivity(sendIntent)
                } else {
                    Toast.makeText(
                        this@DirectChatActivity,
                        "Both fields are mandatory.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}