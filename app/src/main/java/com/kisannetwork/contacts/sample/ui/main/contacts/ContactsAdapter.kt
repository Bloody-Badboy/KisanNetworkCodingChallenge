package com.kisannetwork.contacts.sample.ui.main.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kisannetwork.contacts.sample.R
import com.kisannetwork.contacts.sample.databinding.ItemContactListBinding
import com.kisannetwork.contacts.sample.utils.getRandomColor
import com.kisannetwork.contacts.sample.model.Contact
import com.kisannetwork.contacts.sample.ui.main.MainViewModel

class ContactsAdapter(private val mainViewModel: MainViewModel) :
    ListAdapter<Contact, RecyclerView.ViewHolder>(CONTACTS_COMPARATOR) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): RecyclerView.ViewHolder {
    return ContactsViewHolder.create(parent)
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int
  ) {
    val repoItem = getItem(position)
    if (repoItem != null) {
      (holder as ContactsViewHolder).bind(mainViewModel, repoItem)
    }
  }

  class ContactsViewHolder(private val binding: ItemContactListBinding) :
      RecyclerView.ViewHolder(binding.root) {

    fun bind(
        viewModel: MainViewModel,
        contact: Contact
    ) {

      val shapeColor = getRandomColor(binding.root.context)

      binding.root.setOnClickListener {
        viewModel.onContactsItemClick(Pair(contact, shapeColor))
      }
      binding.materialLetterIcon.letter = contact.firstName
      binding.materialLetterIcon.shapeColor = shapeColor
      binding.contact = contact
    }

    companion object {
      fun create(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemContactListBinding: ItemContactListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_contact_list, parent, false
        )
        return ContactsViewHolder(
            itemContactListBinding
        )
      }
    }
  }

  companion object {
    private val CONTACTS_COMPARATOR = object : DiffUtil.ItemCallback<Contact>() {
      override fun areItemsTheSame(
        oldItem: Contact,
        newItem: Contact
      ): Boolean =
        oldItem.firstName == newItem.firstName &&
            oldItem.lastName == newItem.lastName

      override fun areContentsTheSame(
        oldItem: Contact,
        newItem: Contact
      ): Boolean =
        oldItem == newItem
    }
  }
}

