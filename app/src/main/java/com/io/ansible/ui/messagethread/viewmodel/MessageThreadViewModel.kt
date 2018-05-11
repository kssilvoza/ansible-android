package com.io.ansible.ui.messagethread.viewmodel

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.io.ansible.common.utility.DateUtility
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.data.database.entity.MessageEntity
import com.io.ansible.data.store.ContactStore
import com.io.ansible.data.store.MessageStore
import com.io.ansible.data.store.ProfileStore
import com.io.ansible.network.ansible.model.Profile
import com.io.ansible.ui.messagethread.model.MessageThreadItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by kim.silvoza on 12/04/2018.
 */
class MessageThreadViewModel @Inject constructor(private val messageStore: MessageStore, profileStore: ProfileStore, contactStore: ContactStore) : ViewModel() {
    private lateinit var id : String

    private lateinit var profile : Profile
    private var contactsMap = HashMap<String, ContactEntity>()

    val editTextPublishSubject = PublishSubject.create<String>()
    val messageThreadItemsPublishSubject = PublishSubject.create<List<MessageThreadItem>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(profileStore.observeProfile()
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onProfileChanged))

        compositeDisposable.add(contactStore.observeContacts()
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onContactsChanged))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun setId(id: String) {
        this.id = id
        compositeDisposable.add(messageStore.observeMessages(id)
                .observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMessagesChanged))
    }

    fun sendMessage(messageBody: String) {
        if (messageBody != "") {
            messageStore.sendMessage(profile.id, id, messageBody)
            editTextPublishSubject.onNext("")
        }
    }

    private fun onMessagesChanged(messageEntities: List<MessageEntity>) {
        Log.d("MessageThreadViewModel", "Message Entities Count: ${messageEntities.size}")
        val messageThreadItems = ArrayList<MessageThreadItem>()
        for (index in messageEntities.indices) {
            val messageThreadItem = getMessageThreadItem(messageEntities, index)
            Log.d("MessageThreadViewModel", messageThreadItem.toString())
            messageThreadItems.add(messageThreadItem)
        }

        messageThreadItemsPublishSubject.onNext(messageThreadItems)
    }

    private fun getMessageThreadItem(messageEntities: List<MessageEntity>, index: Int) : MessageThreadItem {
        val messageThreadItem = MessageThreadItem()

        val currentMessage = messageEntities[index]
        val currentMessageDate = DateUtility.convertEpochToDate(currentMessage.timestamp)
        messageThreadItem.date = currentMessageDate

        if (index == 0) {
            messageThreadItem.isDateShown = true
            messageThreadItem.isContactShown = true
        } else {
            val previousMessage = messageEntities[index - 1]
            val previousMessageDate = DateUtility.convertEpochToDate(previousMessage.timestamp)

            if (currentMessageDate != previousMessageDate) {
                messageThreadItem.isDateShown = true
            }

            if (currentMessage.from != previousMessage.from) {
                messageThreadItem.isContactShown = true
            }
        }

        val contact = contactsMap.get(currentMessage.from)
        if (contact != null) {
            messageThreadItem.contactImageUrl = contact.imageUrl
            messageThreadItem.name = contact.displayName
        } else if (profile.id == currentMessage.from) {
            messageThreadItem.contactImageUrl = profile.imageUrl
            messageThreadItem.name = profile.getName()
        } else {
            messageThreadItem.name = currentMessage.from
        }

        messageThreadItem.content = currentMessage.content
        messageThreadItem.time = DateUtility.convertEpochToTime(currentMessage.timestamp)

        return messageThreadItem
    }

    private fun onProfileChanged(profile: Profile) {
        this.profile = profile
    }

    private fun onContactsChanged(contactEntities: List<ContactEntity>) {
        contactsMap.clear()
        for (contactEntity in contactEntities) {
            contactsMap[contactEntity.id] = contactEntity
        }
    }
}