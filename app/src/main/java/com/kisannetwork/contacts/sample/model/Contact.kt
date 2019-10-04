package com.kisannetwork.contacts.sample.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
  @field:SerializedName("first_name")
  val firstName: String,

  @field:SerializedName("last_name")
  val lastName: String,

  @field:SerializedName("phone")
  val phoneNumber: String
) : Parcelable




