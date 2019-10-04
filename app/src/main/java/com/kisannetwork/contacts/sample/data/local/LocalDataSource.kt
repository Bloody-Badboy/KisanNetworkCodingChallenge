package com.kisannetwork.contacts.sample.data.local

import androidx.paging.DataSource
import com.kisannetwork.contacts.sample.data.ContactsDataSource
import com.kisannetwork.contacts.sample.data.local.db.MessagesDatabase
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.model.Message
import com.kisannetwork.contacts.sample.result.Result

class LocalDataSource(
  private val messagesDatabase: MessagesDatabase,
  private val contactsStore: ContactsStore
) : ContactsDataSource {
  override fun getContacts(): List<Contact> {
    return contactsStore.getContacts()
  }

  override fun saveMessage(message: Message) {
    messagesDatabase.messagesDao()
        .insert(message)
  }

  override fun getAllMessages(): DataSource.Factory<Int, Message> {
    return messagesDatabase.messagesDao()
        .getAllMessages()
  }

  override fun sendMessage(
    to: String,
    form: String,
    text: String
  ): Result<Boolean> {
    TODO(
        "not implemented"
    ) //To change body of created functions use File | Settings | File Templates.
  }
}
