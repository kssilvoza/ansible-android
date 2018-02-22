package com.io.ansible.ui.home.component

import com.io.ansible.ui.home.view.fragment.ContactsFragment
import com.io.ansible.ui.home.view.fragment.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by kimsilvozahome on 08/02/2018.
 */
@Module
abstract class HomeModule {
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragmentInjector(): ProfileFragment

    @ContributesAndroidInjector(modules = [ContactsModule::class])
    abstract fun contributeContactsFragmentInjector(): ContactsFragment
}