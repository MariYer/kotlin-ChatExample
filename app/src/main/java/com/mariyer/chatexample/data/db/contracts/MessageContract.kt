package com.mariyer.chatexample.data.db.contracts

object MessageContract {
    const val TABLE_NAME = "messages"

    object Columns {
        const val ID = "id"
        const val USER_ID = "user_id"
        const val IS_OWN = "is_own"
        const val MESSAGE_TEXT = "message_text"
        const val CREATED_AT = "created_at"
    }
}