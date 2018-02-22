package com.io.ansible.di.module

import android.content.Context
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
    fun providePreferences(context: Context): Preferences {
        return Preferences.getInstance(context)
    }
}