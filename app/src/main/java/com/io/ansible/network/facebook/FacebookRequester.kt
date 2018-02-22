package com.io.ansible.network.facebook

import android.content.Intent
import android.support.v4.app.FragmentActivity
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
    private val mFragmentActivity = fragmentActivity

    private val mCallbackManager: CallbackManager

    private val mTokenSubject: PublishSubject<String> = PublishSubject.create()
    private val mErrorSubject: PublishSubject<String> = PublishSubject.create()

    init {
        mCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                if (result.accessToken?.token != null) {
                    mTokenSubject.onNext(result.accessToken.token)
                }
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {
                mErrorSubject.onNext("Error")
            }
        })
    }

    fun signIn() {
        val permissions = mFragmentActivity.getResources().getStringArray(R.array.facebook_permissions)
        LoginManager.getInstance().logInWithReadPermissions(mFragmentActivity, permissions.toMutableList())
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun observeToken() : Observable<String> {
        return mTokenSubject
    }

    fun observeError() : Observable<String> {
        return mErrorSubject
    }
}