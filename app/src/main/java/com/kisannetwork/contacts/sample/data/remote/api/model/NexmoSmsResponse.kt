package com.kisannetwork.contacts.sample.data.remote.api.model

import com.google.gson.annotations.SerializedName

data class NexmoSmsResponse(

  @field:SerializedName("message-count")
  val messageCount: String? = null,

  @field:SerializedName("messages")
  val messages: List<NaxmoSmsMessage?>? = null
)