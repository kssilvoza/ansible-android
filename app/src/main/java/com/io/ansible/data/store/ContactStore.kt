package com.io.ansible.data.store

import com.io.ansible.data.database.dao.ContactDao
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.data.preference.Preferences
import com.io.ansible.network.ansible.api.ContactApi
import com.io.ansible.network.ansible.model.GetContactsResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by kimsilvozahome on 20/02/2018.
 */
class ContactStore(
        private val contactApi: ContactApi,
        private val contactDao: ContactDao,
        private val preferences: Preferences): BaseStore<List<ContactEntity>>() {
    fun observeContacts(): Flowable<List<ContactEntity>> {
        return contactDao.getAll()
    }

    fun refreshContacts(): Single<GetContactsResponse> {
        return contactApi.getContacts()
                .doOnSuccess(this::refreshContactsSuccess)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun loadMoreContacts(): Single<GetContactsResponse> {
        return contactApi.getContacts(preferences.contactPaginationPreference.get().after)
                .doOnSuccess(this::getContactsSuccess)
    }

    private fun refreshContactsSuccess(getContactsResponse: GetContactsResponse) {
        contactDao.deleteAll()
        getContactsSuccess(getContactsResponse)
    }

    private fun getContactsSuccess(getContactsResponse: GetContactsResponse) {
        preferences.contactPaginationPreference.set(getContactsResponse.pagination)
        val contactEntities = mutableListOf<ContactEntity>()
        for (contact in getContactsResponse.contacts) {
            contactEntities.add(ContactEntity(contact))
        }
        contactDao.insertAll(contactEntities)
    }
}