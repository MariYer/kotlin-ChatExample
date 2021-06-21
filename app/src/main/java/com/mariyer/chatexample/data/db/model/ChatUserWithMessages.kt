package com.mariyer.chatexample.data.db.model

import androidx.room.Embedded
import androidx.room.Relation
import com.mariyer.chatexample.data.db.contracts.MessageContract
import com.mariyer.chatexample.data.db.contracts.UserContract
import com.mariyer.chatexample.data.db.model.entities.Message
import com.mariyer.chatexample.data.db.model.entities.ChatUser


class ChatUserWithMessages(
    @Embedded
    val chatUser: ChatUser,
    @Relation(
        parentColumn = UserContract.Columns.ID,
        entityColumn = MessageContract.Columns.USER_ID
    )
    val messages: List<Message>
)
