package com.kisannetwork.contacts.sample.ui.main.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kisannetwork.contacts.sample.R
import com.kisannetwork.contacts.sample.databinding.ItemMessagesListBinding
import com.kisannetwork.contacts.sample.model.Message

class MessagesAdapter : PagedListAdapter<Message, RecyclerView.ViewHolder>(MESSAGES_COMPARATOR) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder {
    return MessagesViewHolder.create(parent)
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {
    val messageItem = getItem(position)
    if (messageItem != null) {
      (holder as MessagesViewHolder).bind(messageItem)
    }
  }

  class MessagesViewHolder(private val binding: ItemMessagesListBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message) = binding.setMessage(message)

    companion object {
      fun create(parent: ViewGroup): MessagesViewHolder {
        val messagesListBinding: ItemMessagesListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_messages_list, parent,
            false
        )
        return MessagesViewHolder(
            messagesListBinding
        )
      }
    }
  }

  companion object {
    private val MESSAGES_COMPARATOR = object : DiffUtil.ItemCallback<Message>() {
      override fun areItemsTheSame(
        oldItem: Message,
        newItem: Message
      ): Boolean =
        oldItem.firstName == newItem.firstName &&
            oldItem.lastName == newItem.lastName &&
            oldItem.message == newItem.message

      override fun areContentsTheSame(
        oldItem: Message,
        newItem: Message
      ): Boolean =
        oldItem == newItem
    }
  }
}