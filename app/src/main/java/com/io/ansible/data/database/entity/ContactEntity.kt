package com.io.ansible.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.io.ansible.network.ansible.model.Contact

/**
 * Created by kimsilvozahome on 23/02/2018.
 */
@Entity(tableName = "contacts")
data class ContactEntity(
        @PrimaryKey var id: String,
        @ColumnInfo(name = "display_name") var displayName: String,
        @ColumnInfo(name = "image_url") var imageUrl: String
) {
    constructor(contact: Contact) : this(contact.id, contact.displayName, contact.imageUrl)
}