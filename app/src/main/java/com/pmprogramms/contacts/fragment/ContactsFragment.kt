package com.pmprogramms.contacts.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmprogramms.contacts.CreateNewContactActivity
import com.pmprogramms.contacts.MainActivity
import com.pmprogramms.contacts.R
import com.pmprogramms.contacts.databinding.FragmentContactsBinding
import com.pmprogramms.contacts.model.Contact
import com.pmprogramms.contacts.mv.ContactsViewModel
import com.pmprogramms.contacts.mv.factory.ContactsViewModelFactory
import com.pmprogramms.contacts.mv.repository.ContactsRepository
import com.pmprogramms.contacts.recyclerview.RecyclerViewAdapter

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var viewModel: ContactsViewModel
    private lateinit var adapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        binding.recyclerContacts.setHasFixedSize(true)
        binding.recyclerContacts.layoutManager = LinearLayoutManager(requireContext())

        binding.addNewContact.setOnClickListener {
            val intentActivity = Intent(context, CreateNewContactActivity::class.java)
            startActivity(intentActivity)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = ContactsRepository(context)
        val factory = ContactsViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory).get(ContactsViewModel::class.java)
        viewModel.getContacts()
        viewModel.contacts.observe(viewLifecycleOwner, { contacts ->
            run {
                adapter = RecyclerViewAdapter(contacts, activity as MainActivity)
                binding.recyclerContacts.adapter = adapter
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getContacts()
        adapter.notifyDataSetChanged()
    }
}