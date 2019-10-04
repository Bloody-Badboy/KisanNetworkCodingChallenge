package com.kisannetwork.contacts.sample.data.remote.api.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class NaxmoSmsMessage(

  @field:SerializedName("message-price")
  val messagePrice: BigDecimal,

  @field:SerializedName("message-id")
  val messageId: String? = null,

  @field:SerializedName("to")
  val to: String? = null,

  @field:SerializedName("remaining-balance")
  val remainingBalance: BigDecimal,

  @field:SerializedName("status")
  val status: String? = null,

  @field:SerializedName("network")
  val network: String? = null,

  @field:SerializedName("error-text")
  val errorText: String? = null,

  @field:SerializedName("client-ref")
  val clientRef: String? = null
)