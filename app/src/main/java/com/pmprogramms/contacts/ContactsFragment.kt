package com.pmprogramms.contacts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmprogramms.contacts.databinding.FragmentContactsBinding
import com.pmprogramms.contacts.mv.ContactsViewModel
import com.pmprogramms.contacts.mv.factory.ContactsViewModelFactory
import com.pmprogramms.contacts.mv.repository.ContactsRepository
import com.pmprogramms.contacts.recyclerview.RecyclerViewAdapter

class ContactsFragment : Fragment() {
    private lateinit var binding: FragmentContactsBinding
    private lateinit var viewModel: ContactsViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        binding.recyclerContacts.setHasFixedSize(true)
        binding.recyclerContacts.layoutManager = LinearLayoutManager(requireContext())
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
                binding.recyclerContacts.adapter = RecyclerViewAdapter(contacts)
            }
        })
    }
}