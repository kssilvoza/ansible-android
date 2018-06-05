package com.io.ansible.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.io.ansible.data.database.model.MessageWithContact
import io.reactivex.Flowable

@Dao
interface MessageWithContactDao {
    @Query("SELECT * FROM messages WHERE thread_id = :threadId")
    fun get(threadId: String): Flowable<List<MessageWithContact>>
}