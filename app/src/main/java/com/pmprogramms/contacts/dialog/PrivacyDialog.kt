package com.pmprogramms.contacts.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.databinding.DialogPrivacyBinding

class PrivacyDialog : DialogFragment() {

    private val privacy = "<b>General</b> <br />\n" +
            "\n" +
            "    Contacts is developed by one main developer.<br />\n" +
            "\n" +
            "    <b>Privacy Policy (\"Policy\") describes how information obtained from users is collected, used and disclosed.</b> <br />\n" +
            "\n" +
            "    By using Contacts, you agree that your personal information will be handled as described in this Policy.<br />\n" +
            "\n" +
            "    <b>Information being collected</b> <br />\n" +
            "\n" +
            "    Contacts does not collect any personal identifiable information.<br />\n" +
            "\n" +
            "    The `WRITE_EXTERNAL_STORAGE` permission is used for saving data on your device.<br />\n" +
            "\n" +
            "    The `READ_EXTERNAL_STORAGE` permission is used for load your photos while you are save/edit contact, and for load fast message values from your device<br />\n" +
            "\n" +
            "    The `READ_CONTACTS` and `WRITE_CONTACTS` permissions are used for accessing contact information, modifying them and creating new contacts.<br />\n" +
            "\n" +
            "    The `SEND_SMS` is for fast sms function.<br />\n" +
            "\n" +
            "    The `CALL_PHONE` is for making call.<br />\n" +
            "\n" +
            "</body>"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogPrivacyBinding = DialogPrivacyBinding.inflate(LayoutInflater.from(context))
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setView(binding.root)
        builder.setTitle("Privacy Policy")
        binding.text.text = Html.fromHtml(privacy, Html.FROM_HTML_MODE_COMPACT)

        builder.setPositiveButton("Ok"
        ) { _, _ ->
            //nothing
        }
        return builder.create()
    }
}