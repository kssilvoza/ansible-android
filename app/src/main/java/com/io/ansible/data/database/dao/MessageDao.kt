package com.io.ansible.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.io.ansible.data.database.entity.MessageEntity
import io.reactivex.Flowable

/**
 * Created by kimsilvozahome on 13/03/2018.
 */
@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun getAll(): Flowable<List<MessageEntity>>

    @Insert
    fun insert(messageEntity: MessageEntity)
}