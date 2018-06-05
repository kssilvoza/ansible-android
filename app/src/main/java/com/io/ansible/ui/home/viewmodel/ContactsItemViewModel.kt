package com.io.ansible.ui.home.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.io.ansible.data.database.entity.ContactEntity

/**
 * Created by kimsilvozahome on 10/04/2018.
 */
class ContactsItemViewModel(val contactEntity: ContactEntity) {
    var image = MutableLiveData<String>()

    var displayName = MutableLiveData<String>()

    init {
        image.value = contactEntity.imageUrl
        displayName.value = contactEntity.displayName
    }
}