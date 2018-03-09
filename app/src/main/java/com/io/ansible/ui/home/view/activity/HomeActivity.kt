package com.io.ansible.ui.home.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.io.ansible.R
import com.io.ansible.messaging.XmppService
import com.io.ansible.ui.home.view.adapter.HomePagerAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initializeViewPager()
        startXmppService()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private fun initializeViewPager() {
        val homePagerAdapter = HomePagerAdapter(supportFragmentManager)
        view_pager.adapter = homePagerAdapter
        view_pager.offscreenPageLimit = 2
        view_pager.currentItem = 0
    }

    private fun startXmppService() {
        startService(Intent(this, XmppService::class.java))
    }
}
