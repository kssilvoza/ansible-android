package com.io.ansible.ui.home.viewmodel

import android.arch.lifecycle.ViewModel
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.data.store.ContactStore
import com.io.ansible.network.ansible.model.AnsibleError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 21/02/2018.
 */
class ContactsViewModel @Inject constructor(private val contactStore: ContactStore): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    var contactsPublishSubject: PublishSubject<List<ContactEntity>> = PublishSubject.create()
        private set

    init {
        compositeDisposable.add(
                contactStore.observeContacts()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onGetContactsSuccess))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    fun refreshContacts() {
        compositeDisposable.add(
                contactStore.refreshContacts()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError { onGetContactsError(AnsibleError(it)) }
                        .subscribe())
    }

    private fun onGetContactsSuccess(contactEntities: List<ContactEntity>) {
        contactsPublishSubject.onNext(contactEntities)
    }

    private fun onGetContactsError(ansibleError: AnsibleError) {

    }
}