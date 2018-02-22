package com.io.ansible.ui.home.viewmodel

import android.arch.lifecycle.ViewModel
import com.io.ansible.data.store.ProfileStore
import com.io.ansible.network.ansible.model.AnsibleError
import com.io.ansible.network.ansible.model.Profile
import com.io.ansible.ui.signin.viewmodel.SignInViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 08/02/2018.
 */
class ProfileViewModel @Inject constructor(profileStore: ProfileStore) : ViewModel() {
    private val mProfileStore = profileStore

    private val mCompositeDisposable = CompositeDisposable()

    var mProfilePublishSubject : PublishSubject<Profile> = PublishSubject.create()
        private set
    var mSpielPublishSubject : PublishSubject<Int> = PublishSubject.create()
        private set

    init {
        mCompositeDisposable.add(mProfileStore.observeProfile().observeOn(AndroidSchedulers.mainThread()).subscribe(this::onGetProfileSuccess))
        mCompositeDisposable.add(mProfileStore.observeError().observeOn(AndroidSchedulers.mainThread()).subscribe(this::onAnsibleError))
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
    }

    fun getProfile() {
        mProfileStore.getProfile()
    }

    private fun onGetProfileSuccess(profile: Profile) {
        mProfilePublishSubject.onNext(profile)
    }

    private fun onAnsibleError(ansibleError: AnsibleError) {
        when {
            ansibleError.kind == AnsibleError.Kind.NETWORK ->
                mSpielPublishSubject.onNext(SignInViewModel.SPIEL_NETWORK_ERROR)
            else ->
                mSpielPublishSubject.onNext(SignInViewModel.SPIEL_DEFAULT_ERROR)
        }
    }
}