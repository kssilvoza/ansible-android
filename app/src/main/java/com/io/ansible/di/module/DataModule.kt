package com.io.ansible.di.module

import android.arch.persistence.room.Room
import android.content.Context
import android.os.Message
import com.io.ansible.data.database.Database
import com.io.ansible.data.database.dao.ContactDao
import com.io.ansible.data.database.dao.MessageDao
import com.io.ansible.data.preference.Preferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kimsilvozahome on 12/01/2018.
 */
@Module
class DataModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "ansible").build()
    }

    @Provides
    @Singleton
    fun provideContactDao(database: Database): ContactDao {
        return database.contactDao()
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: Database): MessageDao {
        return database.messageDao()
    }

    @Provides
    @Singleton
    fun providePreferences(context: Context): Preferences {
        return Preferences.getInstance(context)
    }
}