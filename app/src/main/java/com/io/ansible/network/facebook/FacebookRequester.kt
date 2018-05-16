package com.io.ansible.network.facebook

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.io.ansible.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by kimsilvozahome on 02/02/2018.
 */
class FacebookRequester(fragmentActivity: FragmentActivity) {
    private val fragmentActivity = fragmentActivity

    private val callbackManager: CallbackManager

    private val tokenSubject: PublishSubject<String> = PublishSubject.create()
    private val errorSubject: PublishSubject<String> = PublishSubject.create()

    init {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                if (result.accessToken?.token != null) {
                    tokenSubject.onNext(result.accessToken.token)
                }
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
                errorSubject.onNext("Error")
            }
        })
    }

    fun signIn() {
        val permissions = fragmentActivity.getResources().getStringArray(R.array.facebook_permissions)
        LoginManager.getInstance().logInWithReadPermissions(fragmentActivity, permissions.toMutableList())
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun observeToken() : Observable<String> {
        return tokenSubject
    }

    fun observeError() : Observable<String> {
        return errorSubject
    }
}