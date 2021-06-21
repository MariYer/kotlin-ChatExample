package com.mariyer.chatexample.data.db.model.entities

import androidx.room.*
import com.mariyer.chatexample.data.db.contracts.MessageContract
import com.mariyer.chatexample.data.db.contracts.UserContract
import java.time.Instant

@Entity(
    tableName = MessageContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = ChatUser::class,
            parentColumns = [UserContract.Columns.ID],
            childColumns = [MessageContract.Columns.USER_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(MessageContract.Columns.USER_ID),
        Index(MessageContract.Columns.CREATED_AT)
    ]
)
data class Message(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MessageContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = MessageContract.Columns.USER_ID)
    val userId: Long,
    @ColumnInfo(name = MessageContract.Columns.IS_OWN, defaultValue = "0")
    val isOwn: Boolean,
    @ColumnInfo(name = MessageContract.Columns.MESSAGE_TEXT)
    val messageText: String?,
    @ColumnInfo(name = MessageContract.Columns.CREATED_AT)
    val createdAt: Instant?
)