package com.mariyer.chatexample.data.db

import androidx.room.*
import com.mariyer.chatexample.data.db.contracts.MessageContract
import com.mariyer.chatexample.data.db.model.entities.Message

@Dao
interface MessageDao {

    @Query("SELECT * FROM ${MessageContract.TABLE_NAME} WHERE ${MessageContract.Columns.USER_ID} = :userId")
    suspend fun getMessageByUserId(userId: Long): List<Message>

    @Query("SELECT * FROM ${MessageContract.TABLE_NAME} WHERE ${MessageContract.Columns.ID} = :messageId")
    suspend fun getMessageById(messageId: Long): Message?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<Message>)

    @Update(onConflict = 1)
    suspend fun updateMessage(message: Message): Int

    @Query("DELETE FROM ${MessageContract.TABLE_NAME} WHERE ${MessageContract.Columns.ID} = :messageId")
    suspend fun removeMessage(messageId: Long)
}