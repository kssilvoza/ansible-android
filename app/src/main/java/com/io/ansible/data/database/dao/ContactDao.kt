package com.io.ansible.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.io.ansible.data.database.entity.ContactEntity
import io.reactivex.Flowable

/**
 * Created by kimsilvozahome on 26/02/2018.
 */
@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAll(): Flowable<List<ContactEntity>>

    @Insert
    fun insertAll(contactEntities: List<ContactEntity>)

    @Query("DELETE FROM contacts")
    fun deleteAll()
}