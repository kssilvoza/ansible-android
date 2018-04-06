package com.io.ansible.di.module

import com.io.ansible.ui.home.component.HomeModule
import com.io.ansible.ui.home.view.activity.HomeActivity
import com.io.ansible.ui.signin.component.SignInModule
import com.io.ansible.ui.signin.view.activity.SignInActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by kimsilvozahome on 21/02/2018.
 */
@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [SignInModule::class])
    abstract fun contributeSignInActivityInjector(): SignInActivity

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivityInjector(): HomeActivity
}