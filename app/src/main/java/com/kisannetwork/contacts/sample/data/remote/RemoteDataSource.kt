package com.kisannetwork.contacts.sample.data.remote

import androidx.paging.DataSource
import com.kisannetwork.contacts.sample.data.ContactsDataSource
import com.kisannetwork.contacts.sample.data.remote.api.NexmoSmsService
import com.kisannetwork.contacts.sample.data.remote.api.sendSms
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.model.Message
import com.kisannetwork.contacts.sample.result.Result

class RemoteDataSource(
    private val service: NexmoSmsService,
    private val apiKey: String,
    private val apiSecret: String
) : ContactsDataSource {
    override fun getContacts(): List<Contact> {
        TODO(
            "not implemented"
        ) //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllMessages(): DataSource.Factory<Int, Message> {
        TODO(
            "not implemented"
        ) //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveMessage(message: Message) {
        TODO(
            "not implemented"
        ) //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendMessage(
        to: String,
        form: String,
        text: String
    ): Result<Boolean> {
        return sendSms(service, to, form, text, apiKey, apiSecret)
    }
}
