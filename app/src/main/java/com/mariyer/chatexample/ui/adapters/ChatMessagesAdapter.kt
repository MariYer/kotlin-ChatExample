package com.mariyer.chatexample.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.chatexample.data.db.model.entities.Message

class ChatMessagesAdapter: AsyncListDifferDelegationAdapter<Message>(MessageDiffUtilCallback()) {
    init {
        delegatesManager.addDelegate(ChatMessagesAdapterDelegate())
    }
    class MessageDiffUtilCallback: DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem is Message && newItem is Message && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }
}