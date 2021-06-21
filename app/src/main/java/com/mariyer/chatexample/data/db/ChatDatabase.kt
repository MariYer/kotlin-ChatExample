package com.mariyer.chatexample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mariyer.chatexample.data.db.model.entities.Message
import com.mariyer.chatexample.data.db.model.entities.ChatUser

@Database(
    entities = [ChatUser::class, Message::class],
    version = ChatDatabase.DB_VERSION
)
@TypeConverters(InstantConverter::class, BooleanConverter::class)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "chat-database"
    }
}