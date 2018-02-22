package com.io.ansible.data.store

import android.util.Log
import com.io.ansible.network.ansible.api.ContactApi
import com.io.ansible.network.ansible.model.Contact
import com.io.ansible.network.ansible.model.GetContactsResponse
import io.reactivex.Single

/**
 * Created by kimsilvozahome on 20/02/2018.
 */
class ContactStore(private val contactApi: ContactApi): BaseStore<List<Contact>>() {
    fun getContacts(): Single<List<Contact>> {
        return contactApi.getContacts()
                .map {
                    it.contacts
                }
                .doOnSuccess {
                    // TODO - Save in database
                    Log.d("ContactStore", "Contacts: ${it}")
                }
//        contactApi.getContacts()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::onGetProfileSuccess, this::onError)
    }

    private fun onGetProfileSuccess(getContactsResponse: GetContactsResponse) {
        mResponsePublishSubject.onNext(getContactsResponse.contacts)
    }
}