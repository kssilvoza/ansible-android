package com.io.ansible.ui.messagethread.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.io.ansible.common.utility.DateUtility
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.ui.messagethread.model.MessageThreadItem

/**
 * Created by kim.silvoza on 02/05/2018.
 */
class MessageThreadItemViewModel {
    var isDateShown = MutableLiveData<Boolean>()
    var isContactShown = MutableLiveData<Boolean>()
    var date = MutableLiveData<String>()
    var contactImageUrl = MutableLiveData<String>()
    var contactName = MutableLiveData<String>()
    var text = MutableLiveData<String>()

    fun setMessageThreadItem(messageThreadItem: MessageThreadItem) {
        val messageEntity = messageThreadItem.messageWithContact.messageEntity
        var contactEntity : ContactEntity? = null
        if (messageThreadItem.messageWithContact.from.isNotEmpty()) {
            contactEntity = messageThreadItem.messageWithContact.from[0]
        }

        isDateShown.value = messageThreadItem.isDateShown
        isContactShown.value = messageThreadItem.isContactShown
        date.value = DateUtility.convertEpochToDate(messageEntity.timestamp)
        contactImageUrl.value = contactEntity?.imageUrl
        contactName.value = contactEntity?.displayName
        text.value = messageEntity.content
    }
}