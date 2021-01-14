package com.pmprogramms.contacts.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.databinding.DialogAddMessageBinding
import com.pmprogramms.contacts.databinding.DialogPrivacyBinding
import com.pmprogramms.contacts.helper.DatabaseHandler

class AddMessageDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogAddMessageBinding = DialogAddMessageBinding.inflate(LayoutInflater.from(context))
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setView(binding.root)
        builder.setTitle("Add fast message value")

        builder.setPositiveButton("Save") { _: DialogInterface, _: Int ->
            val databaseHandler = DatabaseHandler(context)
            val value = binding.editText.text.trim().toString()
            databaseHandler.addMessage(value)

        }
        return builder.create()
    }
}