package com.io.ansible.data.store

import com.io.ansible.network.ansible.api.AuthApi
import com.io.ansible.data.preference.Preferences
import com.io.ansible.network.ansible.model.AuthTokens
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by kimsilvozahome on 11/01/2018.
 */
class TokenStore(private val authApi: AuthApi, private val preferences: Preferences) {
    fun observeAuthTokens() : Observable<AuthTokens> {
        return preferences.authTokensPreference.asObservable()
    }

    fun exchangeFacebookToken(accessToken: String): Single<AuthTokens> {
        return authApi.exchangeFacebookToken(accessToken).doOnSuccess(this::onGetTokenSuccess)
    }

    fun exchangeTwitterToken(accessToken: String, accessTokenSecret: String): Single<AuthTokens> {
        return authApi.exchangeTwitterToken(accessToken, accessTokenSecret).doOnSuccess(this::onGetTokenSuccess)
    }

    private fun onGetTokenSuccess(authTokens: AuthTokens) {
        preferences.authTokensPreference.set(authTokens)
    }
}