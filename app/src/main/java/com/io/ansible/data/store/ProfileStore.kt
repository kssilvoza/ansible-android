package com.io.ansible.data.store

import com.io.ansible.data.preference.Preferences
import com.io.ansible.network.ansible.api.ProfileApi
import com.io.ansible.network.ansible.model.Profile
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by kimsilvozahome on 06/02/2018.
 */
class ProfileStore(private val profileApi: ProfileApi, private val preferences: Preferences) {
    fun observeProfile(): Observable<Profile> {
        return preferences.profilePreference.asObservable()
    }

    fun getProfile(): Single<Profile> {
        return profileApi.getProfile().doOnSuccess(this::onGetProfileSuccess)
    }

    private fun onGetProfileSuccess(profile: Profile) {
        preferences.profilePreference.delete()
        preferences.profilePreference.set(profile)
    }
}