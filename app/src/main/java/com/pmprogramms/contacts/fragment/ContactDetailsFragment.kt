package com.pmprogramms.contacts.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.databinding.FragmentContactDetailsBinding
import com.pmprogramms.contacts.dialog.FastMessageDialog
import com.pmprogramms.contacts.helper.DatabaseHandler
import com.pmprogramms.contacts.mv.ContactsViewModel
import com.pmprogramms.contacts.mv.factory.ContactsViewModelFactory
import com.pmprogramms.contacts.mv.repository.ContactsRepository

class ContactDetailsFragment : Fragment() {
    private lateinit var binding: FragmentContactDetailsBinding
    private lateinit var model: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = ContactsRepository(context)
        val factory = ContactsViewModelFactory(repository)

        model = ViewModelProviders.of(requireActivity(), factory).get(ContactsViewModel::class.java)
        model.getSelectedContact().observe(viewLifecycleOwner, { item ->
            run {
                if (item.imageUri != null) binding.photo.setImageURI(Uri.parse(item.imageUri))
                else binding.photo.setImageResource(R.drawable.ic_baseline_person_24)

                binding.name.text = item.name
                binding.number.text = item.number

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
            }
        })
    }
}