package com.io.ansible.messaging

import com.io.ansible.data.database.entity.ContactEntity
import io.reactivex.subjects.PublishSubject

/**
 * Created by kimsilvozahome on 05/04/2018.
 */
object MessageBus {
    var requestPublishSubject: PublishSubject<MessageRequest> = PublishSubject.create()
        private set

    var chatPublishSubject: PublishSubject<List<ContactEntity>> = PublishSubject.create()
        private set

    fun logIn(username: String, password: String) {
        val payload = LogInPayload(username, password)
        val request = MessageRequest(MessageRequest.TYPE_LOG_IN, payload)
        requestPublishSubject.onNext(request)
    }
}