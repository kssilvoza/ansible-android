package com.io.ansible.ui.home.viewmodel

import com.io.ansible.data.database.entity.ContactEntity
import io.reactivex.subjects.PublishSubject

/**
 * Created by kimsilvozahome on 10/04/2018.
 */
class ContactsItemViewModel(val contactEntity: ContactEntity) {
    val imagePublishSubject = PublishSubject.create<String>()

    val displayNamePublishSubject = PublishSubject.create<String>()

    init {
        imagePublishSubject.onNext(contactEntity.imageUrl)
        displayNamePublishSubject.onNext(contactEntity.displayName)
    }
}