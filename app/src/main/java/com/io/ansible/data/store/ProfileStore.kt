package com.io.ansible.data.store

import com.io.ansible.data.preference.Preferences
import com.io.ansible.network.ansible.api.ProfileApi
import com.io.ansible.network.ansible.model.Profile
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by kimsilvozahome on 06/02/2018.
 */
class ProfileStore(private val profileApi: ProfileApi, private val preferences: Preferences) : BaseStore<Profile>() {
    fun observeProfile() : Observable<Profile> {
        return preferences.profilePreference.asObservable()
    }

    fun getProfile() {
        profileApi.getProfile()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetProfileSuccess, this::onError)
    }

    private fun onGetProfileSuccess(profile: Profile) {
        preferences.profilePreference.set(profile)
        mResponsePublishSubject.onNext(profile)
    }
}