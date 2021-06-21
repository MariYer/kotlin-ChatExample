package com.mariyer.chatexample.ui

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mariyer.chatexample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fromNotification = intent.getBooleanExtra("RUN_FROM_NOTIFICATION", false)
        if (fromNotification.not()) {
            val notificationManager: NotificationManager = (this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            notificationManager.cancelAll()
        }
    }
}