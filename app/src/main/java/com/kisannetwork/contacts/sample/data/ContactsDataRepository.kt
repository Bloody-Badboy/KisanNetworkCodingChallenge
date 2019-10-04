package com.kisannetwork.contacts.sample.data

import androidx.paging.DataSource
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.model.Message
import com.kisannetwork.contacts.sample.result.Result

class ContactsDataRepository(
  private val localDataSource: ContactsDataSource,
  private val remoteDataSource: ContactsDataSource
) : ContactsDataSource {

  override fun getContacts(): List<Contact> {
    return localDataSource.getContacts()
  }

  override fun getAllMessages(): DataSource.Factory<Int, Message> =
    localDataSource.getAllMessages()

  override fun saveMessage(message: Message) {
    localDataSource.saveMessage(message)
  }

  override fun sendMessage(
    to: String,
    form: String,
    text: String
  ): Result<Boolean> {
    return remoteDataSource.sendMessage(to, form, text)
  }

}