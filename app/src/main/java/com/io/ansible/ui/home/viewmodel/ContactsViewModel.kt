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
    lateinit var clickedContactEntity: ContactEntity

    var contactsPublishSubject = PublishSubject.create<List<ContactEntity>>()
        private set
    var flowPublishSubject = PublishSubject.create<Pair<Int, Any>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(
                contactStore.observeContacts()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onContactsChanged))
    }

    override fun onCleared() {
        super.onCleared()
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

    private fun onContactsChanged(contactEntities: List<ContactEntity>) {
        contactsPublishSubject.onNext(contactEntities)
    }

    private fun onGetContactsError(ansibleError: AnsibleError) {

    }

    fun onContactClicked(contactEntity: ContactEntity) {
        clickedContactEntity = contactEntity
        flowPublishSubject.onNext(Pair(FLOW_TO_MESSAGE_THREAD_ACTIVITY, contactEntity))
    }

    companion object {
        const val FLOW_TO_MESSAGE_THREAD_ACTIVITY = 1
    }
}