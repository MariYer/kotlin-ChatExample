package com.mariyer.chatexample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.mariyer.chatexample.R
import com.mariyer.chatexample.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainFragment: Fragment(R.layout.fragment_main) {
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notificationsButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_notificationsFragment)
        }
        binding.synchronizeButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_synchronizeFragment)
        }
        binding.chatButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_chatUserListFragment)
        }
        getToken()

        val showChat = activity?.intent?.getBooleanExtra("SHOW_CHAT", false) ?: false
        val userId = activity?.intent?.getLongExtra("USER_ID", 0L) ?: 0L

        if (showChat) {
            val action = MainFragmentDirections.actionMainFragmentToChatFragment(userId)
            findNavController().navigate(action)
        }
    }

    private fun getToken() {
        lifecycleScope.launch {
            val token = getTokenSuspend()
            binding.tokenEditText.setText(token)
        }
    }

    private suspend fun getTokenSuspend(): String? = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
            }
            .addOnFailureListener { exception ->
                continuation.resume(null)
            }
            .addOnCanceledListener {
                continuation.resume(null)
            }
    }
}