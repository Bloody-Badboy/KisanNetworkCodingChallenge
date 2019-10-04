package com.kisannetwork.contacts.sample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.kisannetwork.contacts.sample.data.ContactsDataRepository
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.model.Message
import com.kisannetwork.contacts.sample.result.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repository: ContactsDataRepository) : ViewModel() {


    private val _contactsLiveData = MutableLiveData<List<Contact>>()
    private val _contactsListInteraction = MutableLiveData<Event<Pair<Contact, Int>>>()
    private val _isLoading = MutableLiveData<Event<Boolean>>()

    val messagesLiveData: LiveData<PagedList<Message>>
    val contactsLiveData: LiveData<List<Contact>>
        get() = _contactsLiveData
    val contactsListInteraction: LiveData<Event<Pair<Contact, Int>>>
        get() = _contactsListInteraction
    val isLoading: LiveData<Event<Boolean>>
        get() = _isLoading

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(PLACEHOLDERS)
            .build()

        val pagedListBuilder: LivePagedListBuilder<Int, Message> =
            LivePagedListBuilder<Int, Message>(repository.getAllMessages(), config)

        messagesLiveData = pagedListBuilder.build()

        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _isLoading.value = Event(true)
            _contactsLiveData.value = getContactsFromDatabase()
            _isLoading.value = Event(false)
        }

    }

    private suspend fun getContactsFromDatabase(): List<Contact> {
        return withContext(Dispatchers.IO) {
            repository.getContacts()
        }
    }

    fun onContactsItemClick(contactShapeColorPair: Pair<Contact, Int>) {
        _contactsListInteraction.value = Event(contactShapeColorPair)

    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val PLACEHOLDERS = true
    }
}