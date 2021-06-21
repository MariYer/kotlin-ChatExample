package com.mariyer.chatexample.ui.adapters

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.mariyer.chatexample.R
import com.mariyer.chatexample.data.db.model.entities.Message
import com.mariyer.chatexample.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat.*
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ChatMessagesAdapterDelegate: AbsListItemAdapterDelegate<Message, Message, ChatMessagesAdapterDelegate.Holder>() {

    override fun isForViewType(item: Message, items: MutableList<Message>, position: Int): Boolean {
        return item is Message
    }

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_chat))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(item: Message, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ): RecyclerView.ViewHolder(containerView), LayoutContainer {
        @RequiresApi(Build.VERSION_CODES.O)
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(message: Message) {
            if (message.isOwn.not()) {
                userTimeTextView.text = message.createdAt?.atZone(ZoneId.systemDefault())?.format(formatter)
                userMessageTextView.visibility = View.VISIBLE
                userMessageTextView.text = message.messageText
                ownMessageTextView.visibility = View.GONE
                ownTimeTextView.visibility = View.GONE
            } else {
                ownTimeTextView.text = message.createdAt?.atZone(ZoneId.systemDefault())?.format(formatter)
                ownMessageTextView.visibility = View.VISIBLE
                ownMessageTextView.text = message.messageText
                userMessageTextView.visibility = View.GONE
                userTimeTextView.visibility = View.GONE
            }
        }
    }
}