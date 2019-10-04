package com.kisannetwork.contacts.sample.ui.main.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kisannetwork.contacts.sample.R
import com.kisannetwork.contacts.sample.databinding.FragmentMessagesListBinding
import com.kisannetwork.contacts.sample.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SentMessagesFragment : Fragment() {
  private lateinit var binding: FragmentMessagesListBinding
  private val mainViewModel: MainViewModel by sharedViewModel()
  private lateinit var messagesAdapter: MessagesAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_messages_list, container, false)
    binding.viewModel = mainViewModel
    binding.lifecycleOwner = this


    messagesAdapter = MessagesAdapter()


    with(binding.messagesList) {
      layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
      adapter = messagesAdapter
    }

    mainViewModel.messagesLiveData.observe(this, Observer { t ->
      messagesAdapter.submitList(t)
    })

    return binding.root
  }

}