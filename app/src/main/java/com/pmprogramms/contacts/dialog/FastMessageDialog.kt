package com.pmprogramms.contacts.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.databinding.DialogFastMessageBinding
import com.pmprogramms.contacts.helper.DatabaseHandler
import com.pmprogramms.contacts.model.Message
import java.lang.Exception

//  // open dialog with custom messages
//            // after selected one - press OK and send sms

class FastMessageDialog(private val number: String) : DialogFragment() {
    private lateinit var binding: DialogFastMessageBinding
    private lateinit var arrayMessages: ArrayList<Message>
    private lateinit var adapterList: ArrayAdapter<String>
    private lateinit var listMessagesValues: ArrayList<String>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loadMessages(context)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        binding = DialogFastMessageBinding.inflate(LayoutInflater.from(context))
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Fast message value")
        alertDialog.setPositiveButton("Ok") { _: DialogInterface, _: Int ->
            sendSMS(number)
        }

        alertDialog.setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }

        adapterList = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            listMessagesValues
        )

        alertDialog.setView(binding.root)

        binding.spinnerMessages.adapter = adapterList

        return alertDialog.create()
    }

    private fun sendSMS(number: String) {
        val smsManager = SmsManager.getDefault()
        try {
            smsManager.sendTextMessage(
                number,
                null,
                listMessagesValues[binding.spinnerMessages.selectedItemPosition],
                null,
                null
            )
            Toast.makeText(context, "Send: ${listMessagesValues[binding.spinnerMessages.selectedItemPosition]}", Toast.LENGTH_LONG).show()

        }catch (e: Exception){
            Toast.makeText(context, "Failed send. Try again", Toast.LENGTH_LONG).show()
        }

    }

    private fun loadMessages(context: Context) {
        val databaseHandler = DatabaseHandler(context)
        listMessagesValues = ArrayList()
        listMessagesValues.add("Call me, please")
        arrayMessages = databaseHandler.getMessages()
        arrayMessages.forEach {
            listMessagesValues.add(it.value)
        }
    }
}