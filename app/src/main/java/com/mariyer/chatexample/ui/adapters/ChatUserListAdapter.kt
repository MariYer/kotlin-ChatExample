package com.mariyer.chatexample.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.mariyer.chatexample.data.db.model.entities.ChatUser

class ChatUserListAdapter(
    private val OnItemClick: (id: Long) -> Unit
): AsyncListDifferDelegationAdapter<ChatUser>(ChatUserDiffUtilCallback()) {
    init {
        delegatesManager.addDelegate(ChatUserListAdapterDelegate(OnItemClick))
    }
    class ChatUserDiffUtilCallback: DiffUtil.ItemCallback<ChatUser>() {
        override fun areItemsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean {
            return oldItem is ChatUser && newItem is ChatUser && oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatUser, newItem: ChatUser): Boolean {
            return oldItem == newItem
        }

    }
}