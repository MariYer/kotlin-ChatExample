package com.mariyer.chatexample.ui.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mariyer.chatexample.data.db.ChatRepository
import com.mariyer.chatexample.data.db.model.ChatUserWithMessages
import com.mariyer.chatexample.data.db.model.entities.ChatUser
import com.mariyer.chatexample.data.db.model.entities.Message
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class ChatViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ChatRepository(application)
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

    var currentUserId: Long = 0L
        private set

    val chatUsersWithMessages: LiveData<List<ChatUserWithMessages>>
        get() = repository.getUsersWithMessages(currentUserId)
            .asLiveData(viewModelScope.coroutineContext)

    fun setUserId(userId: Long) {
        currentUserId = userId
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertUserAndMessage(id: Long, userName: String, createdAt: Long, text: String?, isOwn: Boolean = false) {
        val timeString = simpleDateFormat.format(createdAt)

        viewModelScope.launch {
            try {
                repository.insertUserAndMessage(
                    ChatUserWithMessages(
                        ChatUser(id, userName),
                        listOf(
                            Message(id = 0L, userId = id, isOwn = isOwn, messageText = text, Instant.parse(timeString))
                        )
                    ),
                    isOwn
                )
            } catch (t: Throwable) {
                Log.e("ChatViewModel","InsertUserAndMessage: $t")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertMessage(messageText: String, isOwn: Boolean) {
        viewModelScope.launch {
            try {
                repository.saveMessage(
                    Message(
                        id = 0L,
                        userId = currentUserId,
                        isOwn = isOwn,
                        messageText = messageText,
                        createdAt = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
                    )
                )
            } catch (t: Throwable) {
                Log.e("ChatViewModel","InsertMessage: $t")
            }
        }
    }

}