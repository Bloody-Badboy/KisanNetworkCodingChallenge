package com.kisannetwork.contacts.sample.data

import androidx.paging.DataSource
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.model.Message
import com.kisannetwork.contacts.sample.result.Result

interface ContactsDataSource {

  fun getContacts(): List<Contact>

  fun getAllMessages(): DataSource.Factory<Int, Message>

  fun saveMessage(message: Message)

  fun sendMessage(
    to: String,
    form: String,
    text: String
  ): Result<Boolean>

}