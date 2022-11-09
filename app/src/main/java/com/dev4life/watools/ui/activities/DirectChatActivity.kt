package com.dev4life.watools.ui.activities

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

                    val phoneNo = toCode + edtNumber.text.toString().replace(" ", "")
                    Log.e("TAG", "phoneNo: $phoneNo")

                    val sendIntent = Intent(Intent.ACTION_MAIN)
                    sendIntent.component = ComponentName(
                        "com.whatsapp",
                        "com.whatsapp.Conversation"
                    )
                    sendIntent.putExtra("jid", "$phoneNo@s.whatsapp.net")
                    sendIntent.putExtra(Intent.EXTRA_TEXT, edtMessage.text.toString())
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.setPackage("com.whatsapp")
                    sendIntent.type = "text/plain"
                    if (sendIntent.resolveActivity(packageManager) != null) {
                        startActivity(sendIntent)
                    } else {
                        sendIntent.component = ComponentName(
                            "com.whatsapp.w4b",
                            "com.whatsapp.Conversation"
                        )
                        sendIntent.setPackage("com.whatsapp.w4b")
                        if (sendIntent.resolveActivity(packageManager) != null) {
                            startActivity(sendIntent)
                        } else {
                            Toast.makeText(
                                this@DirectChatActivity,
                                "Whatsapp not installed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
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