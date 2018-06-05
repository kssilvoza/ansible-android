package com.io.ansible

import android.app.Activity
import android.app.Application
import android.app.Service
import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import com.io.ansible.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 15/01/2018.
 */
class AnsibleApplication : MultiDexApplication(), HasActivityInjector, HasServiceInjector {
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
        initializeStetho()
    }

    private fun initializeDagger() {
        DaggerApplicationComponent.builder().application(this).build().inject(this)
    }

    private fun initializeStetho() {
        Stetho.initializeWithDefaults(this);
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun serviceInjector(): AndroidInjector<Service> {
        return dispatchingServiceInjector
    }
}