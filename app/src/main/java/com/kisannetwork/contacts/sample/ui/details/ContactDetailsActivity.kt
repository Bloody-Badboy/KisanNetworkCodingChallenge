package com.kisannetwork.contacts.sample.ui.details

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.kisannetwork.contacts.sample.R
import com.kisannetwork.contacts.sample.databinding.ActivityContactDetailsBinding
import com.kisannetwork.contacts.sample.databinding.ContentContactDetailsBottomSheetBinding
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.result.EventObserver
import com.kisannetwork.contacts.sample.utils.activityBinding
import com.kisannetwork.contacts.sample.utils.generateOTP
import com.kisannetwork.contacts.sample.utils.isOnline
import kotlinx.android.synthetic.main.activity_contact_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactDetailsActivity : AppCompatActivity() {

    private val detailsViewModel: ContactsDetailsViewModel by viewModel()
    private val binding by activityBinding<ActivityContactDetailsBinding>(R.layout.activity_contact_details)

    private lateinit var contact: Contact
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)

        handleIncomingIntent(intent)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bottomSheetDialog = BottomSheetDialog(this).apply {
            val bottomSheetBinding =
                DataBindingUtil.inflate<ContentContactDetailsBottomSheetBinding>(
                    layoutInflater,
                    R.layout.content_contact_details_bottom_sheet,
                    null,
                    false
                )
            setContentView(bottomSheetBinding.root)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }

        fab.setOnClickListener {
            sendRequestToServer(bottomSheetDialog)
        }

        detailsViewModel.errorLiveData.observe(this, EventObserver {
            bottomSheetDialog.dismiss()
            Snackbar.make(binding.fab, "Error: ${it.message}", Snackbar.LENGTH_LONG)
                .setAction(R.string.action_retry) {
                    bottomSheetDialog.show()
                    sendRequestToServer(bottomSheetDialog)
                }
                .show()
        })

        detailsViewModel.sentSuccessLiveData.observe(this, EventObserver {
            bottomSheetDialog.dismiss()
            Snackbar.make(
                binding.fab,
                getString(R.string.msg_otp_sent_successfully),
                Snackbar.LENGTH_LONG
            )
                .show()
        })
    }

    private fun handleIncomingIntent(intent: Intent) {
        if (intent.hasExtra(BUNDLE_EXTRA_COLOR) || intent.hasExtra(BUNDLE_EXTRA_CONTACT)) {
            val contact = intent.getParcelableExtra<Contact>(BUNDLE_EXTRA_CONTACT)
            val shapeColor = intent.getIntExtra(BUNDLE_EXTRA_COLOR, Color.BLACK)

            if (contact == null) {
                exitIntentError()
            } else {
                this.contact = contact
                message = getString(
                    R.string.otp_message,
                    generateOTP(6)
                )
                binding.contact = contact
                binding.content.mliContactDetails.letter = contact.firstName
                binding.content.mliContactDetails.shapeColor = shapeColor
                binding.content.tietOptMessage.setText(message)
            }
        } else {
            exitIntentError()
        }
    }

    private fun sendRequestToServer(bottomSheetDialog: BottomSheetDialog) {
        if (isOnline()) {
            bottomSheetDialog.show()
            detailsViewModel.onSendOtpClick(
                contact,
                binding.content.tietOptMessage.text.toString()
            )
        } else {
            bottomSheetDialog.dismiss()
            Snackbar.make(binding.fab, getString(R.string.no_internet), Snackbar.LENGTH_LONG)
                .setAction(R.string.action_retry) {
                    sendRequestToServer(bottomSheetDialog)
                }
                .show()
        }

    }

    private fun exitIntentError() {
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        const val BUNDLE_EXTRA_CONTACT = "extra-contact"
        const val BUNDLE_EXTRA_COLOR = "extra-color"
    }

}
