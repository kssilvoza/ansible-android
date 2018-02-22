package com.io.ansible.di.module

import android.content.Context
import android.support.annotation.NonNull
import com.io.ansible.AnsibleApplication
import com.io.ansible.common.flow.FlowController
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kimsilvozahome on 12/01/2018.
 */
@Module
abstract class ApplicationModule() {
    @Binds
    abstract fun provideContext(application: AnsibleApplication): Context

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        @NonNull
        fun provideFlowController() : FlowController {
            return FlowController()
        }
    }
}