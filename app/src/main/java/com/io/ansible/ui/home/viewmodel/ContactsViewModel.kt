package com.io.ansible.ui.home.viewmodel

import android.arch.lifecycle.ViewModel
import com.io.ansible.data.store.ContactStore
import com.io.ansible.network.ansible.model.Contact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 21/02/2018.
 */
class ContactsViewModel @Inject constructor(private val contactStore: ContactStore): ViewModel() {
    var mContactsPublishSubject : PublishSubject<List<Contact>> = PublishSubject.create()
        private set

    fun getContacts() {
        contactStore.getContacts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetContactsSuccess)
    }

    fun onGetContactsSuccess(contacts: List<Contact>) {
        mContactsPublishSubject.onNext(contacts)
    }
}