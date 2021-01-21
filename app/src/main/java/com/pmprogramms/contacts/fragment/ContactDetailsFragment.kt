package com.pmprogramms.contacts.fragment

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.databinding.FragmentContactDetailsBinding
import com.pmprogramms.contacts.dialog.FastMessageDialog
import com.pmprogramms.contacts.model.Contact
import com.pmprogramms.contacts.mv.ContactsViewModel
import com.pmprogramms.contacts.mv.factory.ContactsViewModelFactory
import com.pmprogramms.contacts.mv.repository.ContactsRepository


class ContactDetailsFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailsBinding
    private lateinit var model: ContactsViewModel

    private lateinit var name: String
    private lateinit var number: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun updateUI(item: Contact) {
        if (item.imageUri != null) binding.photo.setImageURI(Uri.parse(item.imageUri))
        else binding.photo.setImageResource(R.drawable.ic_baseline_person_24)

        name = item.name
        number = item.number

        binding.name.text = name
        binding.number.text = number

        binding.callButton.setOnClickListener {
            val intentCall = Intent(Intent.ACTION_VIEW)
            intentCall.data = Uri.parse("tel:" + item.number);
            context?.startActivity(intentCall)
        }

        binding.messageButton.setOnClickListener {
            val intentSMS = Intent(Intent.ACTION_VIEW)
            intentSMS.type = "vnd.android-dir/mms-sms";
            intentSMS.putExtra("address", item.number);
            context?.startActivity(intentSMS)
        }

        binding.fastMessageButton.setOnClickListener {
            val dialog = FastMessageDialog(item.number)
            dialog.show(parentFragmentManager, "fragmentdialog")
        }

        binding.editContact.setOnClickListener {
            Log.d("onActivityCreated", "onActivityCreated: clicked")
            val mUri = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI,
                item.id.toLong()
            )

            val intent = Intent(Intent.ACTION_EDIT)
            intent.setDataAndType(mUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE)
            intent.putExtra("finishActivityOnSaveCompleted", true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity?.startActivityForResult(intent, 1005)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = ContactsRepository(context)
        val factory = ContactsViewModelFactory(repository)

        model = ViewModelProviders.of(requireActivity(), factory).get(ContactsViewModel::class.java)
        model.getSelectedContact().observe(viewLifecycleOwner, { item ->
            run {
                updateUI(item)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1005 -> {
                Log.d("onActivityResult: ", "onActivityResult: called")
                model.getContacts()
                model.getSelectedContact().observe(viewLifecycleOwner, { item ->
                    run {
                        updateUI(item)
                    }
                })
            }
        }
    }
}