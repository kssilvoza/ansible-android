package com.io.ansible.ui.messagethread.component

import android.arch.lifecycle.ViewModel
import com.io.ansible.di.ViewModelKey
import com.io.ansible.ui.messagethread.viewmodel.MessageThreadViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by kim.silvoza on 12/04/2018.
 */
@Module
abstract class MessageThreadModule {
    @Binds
    @IntoMap
    @ViewModelKey(MessageThreadViewModel::class)
    abstract fun bindMessageThreadViewModel(viewModel: MessageThreadViewModel): ViewModel
}