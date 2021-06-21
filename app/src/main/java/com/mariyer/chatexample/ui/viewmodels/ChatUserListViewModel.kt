package com.mariyer.chatexample.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mariyer.chatexample.data.db.ChatRepository
import com.mariyer.chatexample.data.db.model.entities.ChatUser

class ChatUserListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ChatRepository(application)

    val chatUsers: LiveData<List<ChatUser>>
        get() = repository.getUsers()
            .asLiveData(viewModelScope.coroutineContext)

}