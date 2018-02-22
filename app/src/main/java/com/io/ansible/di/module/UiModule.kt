package com.io.ansible.di.module

import android.arch.lifecycle.ViewModelProvider
import com.io.ansible.common.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by kimsilvozahome on 21/02/2018.
 */
@Module
internal abstract class UiModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}