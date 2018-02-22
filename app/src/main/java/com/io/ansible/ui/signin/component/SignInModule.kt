package com.io.ansible.ui.signin.component

import android.arch.lifecycle.ViewModel
import com.io.ansible.di.ViewModelKey
import com.io.ansible.ui.signin.viewmodel.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by kimsilvozahome on 16/01/2018.
 */
@Module
abstract class SignInModule {
    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel
}