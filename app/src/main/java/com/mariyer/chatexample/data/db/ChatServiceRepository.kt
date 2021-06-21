package com.mariyer.chatexample.data.db

import android.util.Log
import androidx.room.withTransaction
import com.mariyer.chatexample.data.db.model.ChatUserWithMessages
import com.mariyer.chatexample.data.db.model.entities.Message

class ChatServiceRepository {
    private val userDao = Database.instance.userDao()
    private val messageDao = Database.instance.messageDao()

    suspend fun insertUserAndMessage(chatUserWithMessages: ChatUserWithMessages, isOwn: Boolean) {
        Log.d("ChatServiceRepository", "${chatUserWithMessages}")
        Database.instance.withTransaction {
            var userId = chatUserWithMessages.chatUser.id
            var messages = chatUserWithMessages.messages.toMutableList()
            Log.d("ChatServiceRepository", "userId before insert = ${userId}")
            userId = userDao.insertUser(chatUserWithMessages.chatUser)
            Log.d("ChatServiceRepository", "userId after inserty = ${chatUserWithMessages}")
            messages.clear()
            chatUserWithMessages.messages.forEach { message ->
                messages.add(
                    Message(
                        message.id,
                        userId,
                        isOwn,
                        message.messageText,
                        message.createdAt
                    )
                )
            }
            messageDao.insertMessages(messages.toList())
        }
    }
}