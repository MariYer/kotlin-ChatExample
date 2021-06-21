package com.mariyer.chatexample.ui

import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mariyer.chatexample.R
import com.mariyer.chatexample.data.NotificationChannels
import com.mariyer.chatexample.databinding.FragmentNotificationsBinding

class NotificationsFragment: Fragment(R.layout.fragment_notifications) {
    private val binding: FragmentNotificationsBinding by viewBinding(FragmentNotificationsBinding::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendNotificationButton.setOnClickListener {
            if (binding.highPriorityRadioButton.isChecked) {
                showImportantNotification()
            } else {
                showNonImportantNotification()
            }
        }
    }

    private fun showImportantNotification() {
        val title = binding.captionNotificationEditText.text.toString()
        val text = binding.textNotificationEditText.text.toString()

        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.PRIORITY_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(100, 200, 500, 500))
            .setSmallIcon(R.drawable.ic_message)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(PRIORITY_NOTIFICATION_ID, notification)

    }

    private fun showNonImportantNotification() {
        val title = binding.captionNotificationEditText.text.toString()
        val text = binding.textNotificationEditText.text.toString()

        val notification = NotificationCompat.Builder(requireContext(), NotificationChannels.NON_PRIORITY_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_message)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(NON_PRIORITY_NOTIFICATION_ID, notification)

    }

    companion object {
        private const val PRIORITY_NOTIFICATION_ID = 5001
        private const val NON_PRIORITY_NOTIFICATION_ID = 5002

    }
}