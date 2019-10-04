package com.kisannetwork.contacts.sample.di

import android.content.Context
import com.kisannetwork.contacts.sample.R
import com.kisannetwork.contacts.sample.SampleApplication
import com.kisannetwork.contacts.sample.data.ContactsDataRepository
import com.kisannetwork.contacts.sample.data.local.ContactsStore
import com.kisannetwork.contacts.sample.data.local.LocalDataSource
import com.kisannetwork.contacts.sample.data.local.db.MessagesDatabase
import com.kisannetwork.contacts.sample.data.remote.RemoteDataSource
import com.kisannetwork.contacts.sample.data.remote.api.NexmoSmsService
import com.kisannetwork.contacts.sample.ui.details.ContactsDetailsViewModel
import com.kisannetwork.contacts.sample.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val QUALIFIER_APPLICATION_CONTEXT = named("APPLICATION_CONTEXT")
val QUALIFIER_LOCAL_DATA_SOURCE = named("LOCAL_DATA_SOURCE")
val QUALIFIER_REMOTE_DATA_SOURCE = named("REMOTE_DATA_SOURCE")

val QUALIFIER_NEXMO_API_KEY = named("NEXMO_API_KEY")
val QUALIFIER_RNEXMO_API_SECRET = named("NEXMO_API_SECRET")

val appModule = module {

    single(QUALIFIER_APPLICATION_CONTEXT) {
        SampleApplication.getInstance()
    }

    single {
        MessagesDatabase.getInstance(
            get(
                QUALIFIER_APPLICATION_CONTEXT
            )
        )
    }

    single {
        ContactsStore(get())
    }
}

val networkModule = module {
    single {
        NexmoSmsService.create()
    }
}

val repoModule = module {
    single(QUALIFIER_LOCAL_DATA_SOURCE) {
        LocalDataSource(get(), get())
    }

    single(QUALIFIER_REMOTE_DATA_SOURCE) {
        RemoteDataSource(get(), get(QUALIFIER_NEXMO_API_KEY), get(QUALIFIER_RNEXMO_API_SECRET))
    }

    single {
        ContactsDataRepository(
            get(QUALIFIER_LOCAL_DATA_SOURCE), get(
                QUALIFIER_REMOTE_DATA_SOURCE
            )
        )
    }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        ContactsDetailsViewModel(get())
    }
}

val credentialsModule = module {
    single(QUALIFIER_NEXMO_API_KEY) {
        get<Context>().getString(R.string.nexmo_api_key)
    }

    single(QUALIFIER_RNEXMO_API_SECRET) {
        get<Context>().getString(R.string.nexmo_api_secret)
    }
}