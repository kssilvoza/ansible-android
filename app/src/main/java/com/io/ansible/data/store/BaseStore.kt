package com.io.ansible.data.store

import com.google.gson.GsonBuilder
import com.io.ansible.network.ansible.model.AnsibleError
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by kimsilvozahome on 15/01/2018.
 */
abstract class BaseStore<T> {
    protected val mErrorPublishSubject: PublishSubject<AnsibleError> = PublishSubject.create()

    fun observeError(): Observable<AnsibleError> {
        return mErrorPublishSubject
    }

    // TODO - Add way on how to identify which API was called
    protected fun onError(t: Throwable) {
        var ansibleError: AnsibleError
        when (t) {
            is HttpException -> {
                val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                try {
                    ansibleError = gson.fromJson(t.response().errorBody().toString(), AnsibleError::class.java)
                    ansibleError.kind = AnsibleError.Kind.HTTP
                } catch (e: Exception) {
                    ansibleError = AnsibleError(null, null, null, AnsibleError.Kind.UNEXPECTED)
                }
            }
            is IOException -> {
                ansibleError = AnsibleError(null, null, null, AnsibleError.Kind.NETWORK)
            }
            else -> {
                ansibleError = AnsibleError(null, null, null, AnsibleError.Kind.UNEXPECTED)
            }
        }
        mErrorPublishSubject.onNext(ansibleError)
    }
}