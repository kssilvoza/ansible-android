package com.io.ansible.di.component

import android.app.Service
import com.io.ansible.AnsibleApplication
import com.io.ansible.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by kimsilvozahome on 15/01/2018.
 */
// TODO - Add scoping for modules
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilderModule::class,
    ApiModule::class,
    ApplicationModule::class,
    DataModule::class,
    InterceptorModule::class,
    NetworkModule::class,
//    ServiceBuilderModule::class,
    StoreModule::class,
    UiModule::class
])
interface ApplicationComponent: AndroidInjector<AnsibleApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AnsibleApplication): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application : AnsibleApplication)
}