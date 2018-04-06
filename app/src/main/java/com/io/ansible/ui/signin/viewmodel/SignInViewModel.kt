package com.io.ansible.ui.signin.viewmodel

import android.arch.lifecycle.ViewModel
import com.io.ansible.data.store.TokenStore
import com.io.ansible.network.ansible.model.AnsibleError
import com.io.ansible.network.ansible.model.AuthTokens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 15/01/2018.
 */
class SignInViewModel @Inject constructor(private val tokenStore: TokenStore) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var spielPublishSubject: PublishSubject<Int> = PublishSubject.create()
        private set
    var flowPublishSubject: PublishSubject<Int> = PublishSubject.create()
        private set

    init {
        compositeDisposable.add(
                tokenStore.observeAuthTokens()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onGetTokenSuccess))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    fun exchangeFacebookToken(token: String) {
        compositeDisposable.add(
                tokenStore.exchangeFacebookToken(token)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError { onAnsibleError(AnsibleError(it)) }
                        .subscribe())
    }

    fun onGetTokenSuccess(authTokens: AuthTokens) {
        if (authTokens.apiToken != "") {
            flowPublishSubject.onNext(FLOW_TO_HOME_ACTIVITY)
        }
    }

    fun onAnsibleError(ansibleError: AnsibleError) {
        when {
            ansibleError.kind == AnsibleError.Kind.NETWORK ->
                    spielPublishSubject.onNext(SPIEL_NETWORK_ERROR)
            else ->
                    spielPublishSubject.onNext(SPIEL_DEFAULT_ERROR)
        }
    }

    companion object {
        const val SPIEL_NETWORK_ERROR = 100
        const val SPIEL_DEFAULT_ERROR = 101

        const val FLOW_TO_HOME_ACTIVITY = 1
    }
}