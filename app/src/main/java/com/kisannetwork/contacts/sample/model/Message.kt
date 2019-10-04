package com.kisannetwork.contacts.sample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "_id")
  val id: Long = 0,

  @ColumnInfo(name = "first_name")
  val firstName: String,

  @ColumnInfo(name = "last_name")
  val lastName: String,

  @ColumnInfo(name = "phone_number")
  val phoneNumber: String,

  @ColumnInfo(name = "message")
  val message: String,

  @ColumnInfo(name = "sent_at")
  val sentTime: Long = System.currentTimeMillis()
)
