package com.mariyer.chatexample.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mariyer.chatexample.R
import com.mariyer.chatexample.data.db.model.ChatUserWithMessages
import com.mariyer.chatexample.databinding.FragmentChatBinding
import com.mariyer.chatexample.ui.adapters.ChatMessagesAdapter
import com.mariyer.chatexample.ui.viewmodels.ChatViewModel
import com.mariyer.chatexample.utils.AutoClearedValue
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(R.layout.fragment_chat) {
    private val binding: FragmentChatBinding by viewBinding(FragmentChatBinding::class.java)
    private val viewModel: ChatViewModel by viewModels()
    private val args: ChatFragmentArgs by navArgs()
    private var messagesAdapter: ChatMessagesAdapter by AutoClearedValue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setUserId(args.userId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        binding.sendMessageButton.setOnClickListener {
            viewModel.insertMessage(ownMessageEditText.text.toString(),true)
        }
        viewModel.chatUsersWithMessages.observe(viewLifecycleOwner) { list ->
            if (list.size > 0) {
                showUserChat(list[0])
            }
        }
    }

    private fun init() {
        messagesAdapter = ChatMessagesAdapter()
        with(binding.messagesList) {
            adapter = messagesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showUserChat(chatUserWithMessages: ChatUserWithMessages) {
        binding.userNameTextView.text = chatUserWithMessages.chatUser.userName
        messagesAdapter.items = chatUserWithMessages.messages
    }
}