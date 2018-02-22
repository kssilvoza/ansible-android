package com.io.ansible.ui.home.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import com.io.ansible.ui.home.view.fragment.ContactsFragment
import com.io.ansible.ui.home.view.fragment.ProfileFragment

/**
 * Created by kimsilvozahome on 07/02/2018.
 */
class HomePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val fragments: SparseArray<Fragment> = SparseArray<Fragment>()

    init {
        fragments.put(0, ProfileFragment())
        fragments.put(1, ContactsFragment())
    }

    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size()
    }
}