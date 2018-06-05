package com.io.ansible.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.io.ansible.data.database.dao.ContactDao
import com.io.ansible.data.database.dao.MessageDao
import com.io.ansible.data.database.dao.MessageWithContactDao
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.data.database.entity.MessageEntity

/**
 * Created by kimsilvozahome on 26/02/2018.
 */
@Database(entities = [ContactEntity::class, MessageEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao
    abstract fun messageWithContactDao(): MessageWithContactDao
}