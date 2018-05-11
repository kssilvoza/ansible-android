package com.io.ansible.di.module

import com.io.ansible.data.database.dao.ContactDao
import com.io.ansible.data.database.dao.MessageDao
import com.io.ansible.network.ansible.api.AuthApi
import com.io.ansible.data.preference.Preferences
import com.io.ansible.data.store.ContactStore
import com.io.ansible.data.store.MessageStore
import com.io.ansible.data.store.ProfileStore
import com.io.ansible.data.store.TokenStore
import com.io.ansible.network.ansible.api.ContactApi
import com.io.ansible.network.ansible.api.ProfileApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kimsilvozahome on 12/01/2018.
 */
@Module
class StoreModule {
    @Provides
    @Singleton
    fun provideTokenStore(authApi: AuthApi, preferences: Preferences): TokenStore {
        return TokenStore(authApi, preferences)
    }

    @Provides
    @Singleton
    fun provideProfileStore(profileApi: ProfileApi, preferences: Preferences): ProfileStore {
        return ProfileStore(profileApi, preferences)
    }

    @Provides
    @Singleton
    fun provideContactStore(contactApi: ContactApi, contactDao: ContactDao, preferences: Preferences): ContactStore {
        return ContactStore(contactApi, contactDao, preferences)
    }

    @Provides
    @Singleton
    fun provideMessageStore(messageDao: MessageDao): MessageStore {
        return MessageStore(messageDao)
    }
}