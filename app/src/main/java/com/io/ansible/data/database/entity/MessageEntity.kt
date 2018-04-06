package com.io.ansible.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by kimsilvozahome on 09/03/2018.
 */
@Entity(tableName = "messages")
data class MessageEntity(
        @PrimaryKey var id: String,
        @ColumnInfo(name = "from") var from: String,
        @ColumnInfo(name = "content") var content: String
) {
}