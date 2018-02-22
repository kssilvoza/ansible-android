package com.io.ansible.ui.home.component

import android.arch.lifecycle.ViewModel
import com.io.ansible.di.ViewModelKey
import com.io.ansible.ui.home.viewmodel.ContactsViewModel
import com.io.ansible.ui.home.viewmodel.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by kimsilvozahome on 22/02/2018.
 */
@Module
abstract class ContactsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    abstract fun bindContactsViewModel(viewModel: ContactsViewModel): ViewModel
}