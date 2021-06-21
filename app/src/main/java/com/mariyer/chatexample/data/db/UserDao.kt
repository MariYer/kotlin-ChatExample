package com.mariyer.chatexample.data.db

import androidx.room.*
import com.mariyer.chatexample.data.db.contracts.UserContract
import com.mariyer.chatexample.data.db.model.ChatUserWithMessages
import com.mariyer.chatexample.data.db.model.entities.ChatUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM ${UserContract.TABLE_NAME}")
    fun getAllUsers(): Flow<List<ChatUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(chatUser: ChatUser): Long

    @Update
    suspend fun updateUser(chatUser: ChatUser)

    @Query("DELETE FROM ${UserContract.TABLE_NAME} WHERE ${UserContract.Columns.ID} = :userId")
    suspend fun removeUser(userId: Long)

    @Query("SELECT * FROM ${UserContract.TABLE_NAME} WHERE ${UserContract.Columns.ID}=:userId")
    suspend fun getUserByUserId(userId: Long): ChatUser?

    @Query("SELECT * FROM ${UserContract.TABLE_NAME} WHERE ${UserContract.Columns.ID}=:userId")
    fun getUsersWithMessages(userId: Long): Flow<List<ChatUserWithMessages>>


}