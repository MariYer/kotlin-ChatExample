package com.mariyer.chatexample.ui

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mariyer.chatexample.R
import com.mariyer.chatexample.data.NetworkReceiver
import com.mariyer.chatexample.data.NotificationChannels
import com.mariyer.chatexample.databinding.FragmentSynchronizeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SynchronizeFragment: Fragment(R.layout.fragment_synchronize) {
    private val binding: FragmentSynchronizeBinding by viewBinding(FragmentSynchronizeBinding::class.java)
    private lateinit var receiver: NetworkReceiver

    private var mobileConnected: Boolean = false
    private var wifiConnected: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.synchronizeButton.setOnClickListener {
            checkNetwork()
        }
        receiver = NetworkReceiver()
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireContext().registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unregisterReceiver(receiver)
    }

    private fun checkNetwork() {
        if (isOnline().not()) {
            Toast.makeText(requireContext(), "Отсутствует сеть. Подключитесь к интернету.", Toast.LENGTH_LONG)
        } else {
            showSyncNotification()
        }
    }

    private fun showSyncNotification() {
        binding.synchronizeButton.isEnabled = false

        val notificationBuilder = NotificationCompat.Builder(requireContext(), NotificationChannels.PRIORITY_CHANNEL_ID)
            .setContentTitle("Синхронизация")
            .setContentText("Выполняется синхронизация")
            .setSmallIcon(R.drawable.ic_sync)

        val maxProgress = 10
        lifecycleScope.launch {
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()

                NotificationManagerCompat.from(requireContext())
                    .notify(PROGRESS_NOTIFICATION_ID, notification)

                delay(500)
            }
            val finallyNotification = notificationBuilder
                .setContentText("Синхронизация завершена")
                .setProgress(0, 0, false)
                .build()

            binding.synchronizeButton.isEnabled = true

            NotificationManagerCompat.from(requireContext())
                .notify(PROGRESS_NOTIFICATION_ID, finallyNotification)

            delay(1000)

            NotificationManagerCompat.from(requireContext())
                .cancel(PROGRESS_NOTIFICATION_ID)
        }
    }

    @Suppress("DEPRECATION")
    private fun isOnline(): Boolean {
        val connMgr = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

    companion object {
        private const val PROGRESS_NOTIFICATION_ID = 104
    }

}