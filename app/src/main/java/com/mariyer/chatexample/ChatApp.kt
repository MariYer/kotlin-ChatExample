package com.mariyer.chatexample

import android.app.Application
import com.mariyer.chatexample.data.NotificationChannels
import com.mariyer.chatexample.data.db.Database

class ChatApp: Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationChannels.create(this)
        Database.init(this)
    }
}