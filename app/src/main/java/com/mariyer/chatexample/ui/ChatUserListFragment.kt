package com.mariyer.chatexample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mariyer.chatexample.R
import com.mariyer.chatexample.databinding.FragmentChatUsersBinding
import com.mariyer.chatexample.ui.adapters.ChatUserListAdapter
import com.mariyer.chatexample.ui.viewmodels.ChatUserListViewModel
import com.mariyer.chatexample.utils.AutoClearedValue

class ChatUserListFragment: Fragment(R.layout.fragment_chat_users) {

    private val binding: FragmentChatUsersBinding by viewBinding(FragmentChatUsersBinding::class.java)
    private val viewModel: ChatUserListViewModel by viewModels()
    private var usersAdapter: ChatUserListAdapter by AutoClearedValue()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        viewModel.chatUsers.observe(viewLifecycleOwner) { usersAdapter.items = it}
    }

    fun init() {
        usersAdapter = ChatUserListAdapter { id ->
            val action = ChatUserListFragmentDirections.actionChatUserListFragmentToChatFragment(id)
            findNavController().navigate(action)
        }
        with(binding.usersList) {
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}