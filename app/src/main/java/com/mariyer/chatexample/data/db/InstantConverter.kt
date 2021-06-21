package com.mariyer.chatexample.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant

class InstantConverter {

    @TypeConverter
    fun instantToString(date: Instant?): String? {
        return if (date==null) {null} else {
            date.toString()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun stringToInstant(dateStr: String?): Instant? {
        return if (dateStr==null) {null} else {
            Instant.parse(dateStr)
        }
    }
}