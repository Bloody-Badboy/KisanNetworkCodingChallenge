package com.kisannetwork.contacts.sample.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.model.Contacts
import java.io.InputStreamReader

class ContactsStore(private val context: Context) {

  private val gson = Gson()

  fun getContacts(): List<Contact> {
    val inputStream = context.assets.open("sample_contacts.json")
    val jsonReader = JsonReader(InputStreamReader(inputStream))
    val contacts = gson.fromJson<Contacts>(jsonReader, Contacts::class.java)
    return contacts.contacts.sortedBy {
      it.firstName
    }
  }
}