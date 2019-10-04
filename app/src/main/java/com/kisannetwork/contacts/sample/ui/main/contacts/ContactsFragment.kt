package com.kisannetwork.contacts.sample.ui.main.contacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisannetwork.contacts.sample.R
import com.kisannetwork.contacts.sample.databinding.FragmentContactsListBinding
import com.kisannetwork.contacts.sample.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ContactsFragment : Fragment() {

  private lateinit var binding: FragmentContactsListBinding
  private val mainViewModel: MainViewModel by sharedViewModel()
  private lateinit var contactsAdapter: ContactsAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_contacts_list, container, false)
    binding.viewModel = mainViewModel
    binding.lifecycleOwner = this


    contactsAdapter = ContactsAdapter(mainViewModel)


    with(binding.contactsList) {
      layoutManager = LinearLayoutManager(context)
      adapter = contactsAdapter
    }

    mainViewModel.contactsLiveData.observe(
        this, Observer { t ->
      contactsAdapter.submitList(t)
    })

    mainViewModel.isLoading.observe(this, Observer { t ->
      Log.d("ContactsFragment", " Loading: " + t.peekContent())
    })

    return binding.root
  }

}
