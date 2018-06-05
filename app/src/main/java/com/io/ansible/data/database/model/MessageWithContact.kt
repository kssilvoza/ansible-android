package com.io.ansible.data.database.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.data.database.entity.MessageEntity

class MessageWithContact {
    @Embedded
    lateinit var messageEntity: MessageEntity
    @Relation(parentColumn = "from", entityColumn = "id")
    lateinit var from: List<ContactEntity>
}