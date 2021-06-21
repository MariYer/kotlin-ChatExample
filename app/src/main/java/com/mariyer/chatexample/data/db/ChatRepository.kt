package com.mariyer.chatexample.data.db

import android.content.Context
import androidx.room.withTransaction
import com.mariyer.chatexample.data.db.model.ChatUserWithMessages
import com.mariyer.chatexample.data.db.model.entities.ChatUser
import com.mariyer.chatexample.data.db.model.entities.Message
import kotlinx.coroutines.flow.Flow

class ChatRepository(context: Context) {
    private val userDao = Database.instance.userDao()
    private val messageDao = Database.instance.messageDao()

    fun getUsersWithMessages(userId: Long): Flow<List<ChatUserWithMessages>> {
        return userDao.getUsersWithMessages(userId)
    }

    fun getUsers(): Flow<List<ChatUser>> {
        return userDao.getAllUsers()
    }

    suspend fun saveMessage(message: Message) {
        messageDao.insertMessages(listOf(message))
    }

    suspend fun insertUserAndMessage(chatUserWithMessages: ChatUserWithMessages, isOwn: Boolean) {
        Database.instance.withTransaction {
            var userId = chatUserWithMessages.chatUser.id
            var messages = chatUserWithMessages.messages.toMutableList()
            if (userId == 0L) {
                userId = userDao.insertUser(chatUserWithMessages.chatUser)
            }
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