package com.mariyer.chatexample.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.chatexample.R
import com.mariyer.chatexample.data.db.model.entities.ChatUser
import com.mariyer.chatexample.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_user.*

class ChatUserListAdapterDelegate(
    private val OnItemClick: (id: Long) -> Unit
) :
    AbsListItemAdapterDelegate<ChatUser, ChatUser, ChatUserListAdapterDelegate.Holder>() {

    override fun isForViewType(
        item: ChatUser,
        items: MutableList<ChatUser>,
        position: Int
    ): Boolean {
        return item is ChatUser
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_chat_user),OnItemClick)
    }

    override fun onBindViewHolder(item: ChatUser, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View,
        private val OnItemClick: (id: Long) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        private var currentId: Long? = null

        init {
            containerView.setOnClickListener {
                currentId?.let { id ->
                    OnItemClick(id)
                }
            }
        }

        fun bind(chatUser: ChatUser) {
            currentId = chatUser.id
            userNameTextView.text = chatUser.userName
        }
    }
}
