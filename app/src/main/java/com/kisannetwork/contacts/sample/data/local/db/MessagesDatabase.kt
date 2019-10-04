
package com.kisannetwork.contacts.sample.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kisannetwork.contacts.sample.model.Message

@Database(
    entities = [Message::class],
    version = 1,
    exportSchema = false
)
abstract class MessagesDatabase : RoomDatabase() {

  abstract fun messagesDao(): MessagesDao

  companion object {

    @Volatile
    private var INSTANCE: MessagesDatabase? = null

    fun getInstance(context: Context): MessagesDatabase =
      INSTANCE ?: synchronized(this) {
        INSTANCE
            ?: buildDatabase(context).also { INSTANCE = it }
      }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(
          context.applicationContext,
          MessagesDatabase::class.java, "messages.db"
      )
          .build()
  }
}
