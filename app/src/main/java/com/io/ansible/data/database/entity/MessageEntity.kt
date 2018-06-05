package com.io.ansible.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by kimsilvozahome on 09/03/2018.
 */
@Entity(tableName = "messages")
data class MessageEntity(
        @ColumnInfo(name = "thread_id") var threadId: String,
        @ColumnInfo(name = "from") var from: String,
        @ColumnInfo(name = "timestamp") var timestamp: Long,
        @ColumnInfo(name = "direction") var direction: Int,
        @ColumnInfo(name = "content") var content: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    companion object {
        const val DIRECTION_INCOMING = 1
        const val DIRECTION_OUTGOING = 2
    }
}