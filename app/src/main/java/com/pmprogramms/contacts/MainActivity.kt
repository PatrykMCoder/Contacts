package com.pmprogramms.contacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmprogramms.contacts.databinding.ActivityMainBinding
import com.pmprogramms.contacts.helper.ContactsHelper
import com.pmprogramms.contacts.recyclerview.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    var adapter: RecyclerViewAdapter? = null

    private lateinit var contactsHelper: ContactsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerContacts.layoutManager = LinearLayoutManager(this)
        binding.recyclerContacts.setHasFixedSize(true)

        contactsHelper = ContactsHelper.instance
        adapter = RecyclerViewAdapter(contactsHelper.getDataAboutNumbers(contentResolver))
        binding.recyclerContacts.adapter = adapter
    }
}


