package com.pmprogramms.contacts.mv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmprogramms.contacts.model.Contact
import com.pmprogramms.contacts.mv.repository.ContactsRepository

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    private val selectedContact = MutableLiveData<Contact>()

    val contacts: LiveData<List<Contact>> get() = _contacts

    fun getContacts() {
        val contacts = repository.getContacts()
        _contacts.value = contacts
    }

    fun setSelectedContact(contact: Contact) {
        selectedContact.value = contact
    }

    fun getSelectedContact(): MutableLiveData<Contact> {
        return selectedContact
    }
}