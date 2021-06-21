package com.mariyer.chatexample.data.db.model.entities

import androidx.room.*
import com.mariyer.chatexample.data.db.contracts.UserContract

@Entity(tableName = UserContract.TABLE_NAME)
data class ChatUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UserContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = UserContract.Columns.USER_NAME)
    val userName: String
)