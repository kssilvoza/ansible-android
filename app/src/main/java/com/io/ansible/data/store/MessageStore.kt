package com.io.ansible.data.store

import com.io.ansible.data.database.dao.MessageDao
import com.io.ansible.data.database.entity.MessageEntity
import io.reactivex.Flowable

/**
 * Created by kimsilvozahome on 09/03/2018.
 */
class MessageStore(private val messageDao: MessageDao) {
    fun observeMessages(): Flowable<List<MessageEntity>> {
        return messageDao.getAll()
    }
}