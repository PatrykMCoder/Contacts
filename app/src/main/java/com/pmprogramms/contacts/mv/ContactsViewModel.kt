package com.pmprogramms.contacts.mv

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmprogramms.contacts.model.Contact
import com.pmprogramms.contacts.mv.repository.ContactsRepository

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    fun getContacts() {
        val contacts = repository.getContacts()
        _contacts.value = contacts
    }
}