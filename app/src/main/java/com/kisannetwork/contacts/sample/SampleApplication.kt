package com.kisannetwork.contacts.sample

import android.app.Application
import com.kisannetwork.contacts.sample.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SampleApplication : Application() {

    companion object {

        private lateinit var sInstance: SampleApplication

        fun getInstance(): SampleApplication {
            return sInstance
        }

    }

    override fun onCreate() {
        super.onCreate()

        sInstance = this

        startKoin {
            androidLogger()
            androidContext(this@SampleApplication)
            modules(
                listOf(
                    appModule,
                    credentialsModule,
                    networkModule,
                    repoModule,
                    viewModelModule
                )
            )
        }
    }

}