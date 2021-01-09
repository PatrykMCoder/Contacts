package com.pmprogramms.contacts.mv.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pmprogramms.contacts.mv.ContactsViewModel
import com.pmprogramms.contacts.mv.repository.ContactsRepository

class ContactsViewModelFactory (
    private val repository: ContactsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactsViewModel(repository) as T
    }
}