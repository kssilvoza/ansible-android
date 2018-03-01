package com.io.ansible.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.io.ansible.data.database.dao.ContactDao
import com.io.ansible.data.database.entity.ContactEntity

/**
 * Created by kimsilvozahome on 26/02/2018.
 */
@Database(entities = [ContactEntity::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}