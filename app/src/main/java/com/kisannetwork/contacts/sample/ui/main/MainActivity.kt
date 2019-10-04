package com.kisannetwork.contacts.sample.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kisannetwork.contacts.sample.R
import com.kisannetwork.contacts.sample.R.layout
import com.kisannetwork.contacts.sample.databinding.ActivityMainBinding
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.result.EventObserver
import com.kisannetwork.contacts.sample.ui.details.ContactDetailsActivity
import com.kisannetwork.contacts.sample.ui.main.contacts.ContactsFragment
import com.kisannetwork.contacts.sample.ui.main.messages.SentMessagesFragment
import com.kisannetwork.contacts.sample.utils.activityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainBinding by activityBinding<ActivityMainBinding>(
        layout.activity_main
    )
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(mainBinding.toolbar)

        with(mainBinding) {
            container.adapter = SectionsPagerAdapter(
                supportFragmentManager
            ).apply {
                addFragment(ContactsFragment(), "Contacts")
                addFragment(SentMessagesFragment(), "Messages")
            }
            tabs.setupWithViewPager(mainBinding.container)
        }


        mainViewModel.contactsListInteraction.observe(this, EventObserver { contactShapeColorPair ->
            launchDetailsActivity(contactShapeColorPair)
        })
    }

    private fun launchDetailsActivity(contactShapeColorPair: Pair<Contact, Int>) {
        Intent(this, ContactDetailsActivity::class.java).apply {
            putExtra(ContactDetailsActivity.BUNDLE_EXTRA_CONTACT, contactShapeColorPair.first)
            putExtra(ContactDetailsActivity.BUNDLE_EXTRA_COLOR, contactShapeColorPair.second)
            startActivity(this)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}
