package com.mariyer.chatexample.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    val PRIORITY_CHANNEL_ID = "priority"
    val NON_PRIORITY_CHANNEL_ID = "non-priority"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createMessageChannel(context)
            createNewsChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNewsChannel(context: Context) {
        val channelName = "Messages"
        val channelDescription = "Urgent messages"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(PRIORITY_CHANNEL_ID, channelName, channelPriority)
            .apply {
                description = channelDescription
                enableVibration(true)
                vibrationPattern = longArrayOf(100, 200, 500, 500)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createMessageChannel(context: Context) {
        val channelName = "News"
        val channelDescription = "App news"
        val channelPriority = NotificationManager.IMPORTANCE_LOW

        val channel = NotificationChannel(NON_PRIORITY_CHANNEL_ID, channelName, channelPriority)
            .apply {
                description = channelDescription
            }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

}