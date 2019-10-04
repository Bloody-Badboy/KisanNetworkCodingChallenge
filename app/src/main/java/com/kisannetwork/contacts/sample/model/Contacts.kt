package com.kisannetwork.contacts.sample.model

import com.google.gson.annotations.SerializedName

data class Contacts(
  @field:SerializedName("contacts")
  val contacts: List<Contact>
)