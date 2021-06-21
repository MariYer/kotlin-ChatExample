package com.mariyer.chatexample.data

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mariyer.chatexample.R
import com.mariyer.chatexample.data.db.ChatServiceRepository
import com.mariyer.chatexample.data.db.Database
import com.mariyer.chatexample.data.db.model.ChatUserWithMessages
import com.mariyer.chatexample.data.db.model.entities.ChatUser
import com.mariyer.chatexample.data.db.model.entities.Message
import com.mariyer.chatexample.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class MessagingService : FirebaseMessagingService() {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
    private var repository: ChatServiceRepository? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("MessagingService", "onCreate: Database init...")
        Database.init(this)
        repository = ChatServiceRepository()
        Log.d("MessagingService", "onCreate: Database = ${Database.instance}")
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("MessagingService", "onMessageReceived: started...")
        val messageType = message.data["type"]
        Log.d("MessagingService", "onMessageReceived: messageType = $messageType")
        if (messageType == "action") {
            val title: String? = message.data["title"]
            val description: String? = message.data["description"]
            val imageUrl: String? = message.data["imageUrl"]
            showActionNotification(title, description, imageUrl)
        } else if (messageType == "message") {
            val userId: Long? = message.data["userId"]?.toLongOrNull()
            val userName: String? = message.data["userName"]
            var createdAt: Long? = message.data["createdAt"]?.toLongOrNull()
            createdAt?.let {
                createdAt = it * 1000L
            }
            val text = message.data["text"]

            userId?.let { id ->
                showMessageNotification(id, userName, createdAt, text)
                if (userName != null && createdAt != null) {
                    addUserAndMessageToDatabase(id, userName!!, createdAt!!, text)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        repository = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addUserAndMessageToDatabase(
        id: Long,
        userName: String,
        createdAt: Long,
        text: String?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            repository?.insertUserAndMessage(
                ChatUserWithMessages(
                    ChatUser(id, userName),
                    listOf(
                        Message(
                            0L,
                            id,
                            false,
                            text,
                            Instant.parse(simpleDateFormat.format(createdAt))
                        )
                    )
                ),
                false
            )
        }
    }

    private fun getIntent(showChat: Boolean = false, userId: Long? = null): PendingIntent {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("RUN_FROM_NOTIFICATION", true)
        if (showChat) {
            userId?.let {
                intent.putExtra("SHOW_CHAT", true)
                intent.putExtra("USER_ID", it)
            }
        }
        return PendingIntent.getActivity(this, 123, intent, 0)
    }

    private fun showMessageNotification(
        userId: Long,
        userName: String?,
        createdAt: Long?,
        text: String?
    ) {
        val pendingIntent = getIntent(true, userId)
        Log.d("MessagingService", "showMessageNotification: started...")
        val notification =
            NotificationCompat.Builder(this, NotificationChannels.PRIORITY_CHANNEL_ID)
                .setContentTitle("Новое сообщение от $userName")
                .setContentText("${simpleDateFormat.format(createdAt)} $text")
                .setSmallIcon(R.drawable.ic_message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        NotificationManagerCompat.from(this)
            .notify(userId.toInt(), notification)


    }

    private fun showActionNotification(title: String?, description: String?, imageUrl: String?) {
        val pendingIntent = getIntent()

        Log.d("MessagingService", "showActionNotification: started...")
        val notificationBuilder =
            NotificationCompat.Builder(this, NotificationChannels.NON_PRIORITY_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_money)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        // уведомление без картинки
        val notification = notificationBuilder
            .build()
        NotificationManagerCompat.from(this)
            .notify(ACTION_NOTIFICATION_ID, notification)

        // загрузим картинку и уведомление с картинкой
        val mainHandler = Handler(Looper.getMainLooper())
        val backgroundThread: HandlerThread = HandlerThread("background thread").apply {
            start()
        }
        val handler = Handler(backgroundThread.looper)
        imageUrl?.let { url ->
            handler.post {
                Log.d("MessagingService", "showActionNotification: imageUrl=$imageUrl")
                val bitmap = Glide.with(this)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()
                Log.d("MessagingService", "showActionNotification: bitmap is loaded")
                mainHandler.post {
                    val notificationWithImage = notificationBuilder
                        .setLargeIcon(bitmap)
                        .build()
                    NotificationManagerCompat.from(this)
                        .notify(ACTION_NOTIFICATION_ID, notificationWithImage)
                }
            }

        }
    }

    companion object {
        private const val ACTION_NOTIFICATION_ID = 23001
    }
}