package com.kisannetwork.contacts.sample.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisannetwork.contacts.sample.BuildConfig
import com.kisannetwork.contacts.sample.data.ContactsDataRepository
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.model.Message
import com.kisannetwork.contacts.sample.result.Event
import com.kisannetwork.contacts.sample.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsDetailsViewModel(private val repository: ContactsDataRepository) : ViewModel() {


    private val _sentSuccessLiveData = MutableLiveData<Event<Boolean>>()
    private val _errorLiveData = MutableLiveData<Event<Exception>>()

    val sentSuccessLiveData: LiveData<Event<Boolean>>
        get() = _sentSuccessLiveData
    val errorLiveData: LiveData<Event<Exception>>
        get() = _errorLiveData

    private suspend fun doSendOtpRequest(
        contact: Contact,
        message: String
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            repository.sendMessage(
                contact.phoneNumber, "NEXMO", message
            )
        }
    }

    private suspend fun saveMessageLocally(
        contact: Contact,
        message: String
    ) {
        withContext(Dispatchers.IO) {
            repository.saveMessage(
                Message(
                    firstName = contact.firstName,
                    lastName = contact.lastName,
                    phoneNumber = contact.phoneNumber,
                    message = message
                )
            )
        }
    }

    fun onSendOtpClick(
        contact: Contact,
        message: String
    ) {
        viewModelScope.launch {
            val result: Result<Boolean> = doSendOtpRequest(contact, message)
            if ((result as? Result.Success)?.data == true) {
                saveMessageLocally(contact, message)
                _sentSuccessLiveData.value = Event(true)
            } else {
                _errorLiveData.value =
                    Event((result as? Result.Error)?.exception ?: Exception("Unknown error"))
            }
        }
    }
}
