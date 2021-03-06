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
class ProfileViewModel @Inject constructor(private val profileStore: ProfileStore) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var profilePublishSubject: PublishSubject<Profile> = PublishSubject.create()
        private set
    var spielPublishSubject: PublishSubject<Int> = PublishSubject.create()
        private set

    init {
        compositeDisposable.add(profileStore.observeProfile().observeOn(AndroidSchedulers.mainThread()).subscribe(this::onGetProfileSuccess))
        compositeDisposable.add(profileStore.observeError().observeOn(AndroidSchedulers.mainThread()).subscribe(this::onAnsibleError))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    fun getProfile() {
        profileStore.getProfile()
    }

    private fun onGetProfileSuccess(profile: Profile) {
        profilePublishSubject.onNext(profile)
    }

    private fun onAnsibleError(ansibleError: AnsibleError) {
        when {
            ansibleError.kind == AnsibleError.Kind.NETWORK ->
                spielPublishSubject.onNext(SignInViewModel.SPIEL_NETWORK_ERROR)
            else ->
                spielPublishSubject.onNext(SignInViewModel.SPIEL_DEFAULT_ERROR)
        }
    }
}