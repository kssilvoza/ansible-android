package com.io.ansible.message

import io.reactivex.subjects.PublishSubject
import org.jivesoftware.smack.packet.Message
import org.jxmpp.jid.EntityBareJid

/**
 * Created by kimsilvozahome on 05/04/2018.
 */
object MessageBus {
    var requestPublishSubject: PublishSubject<MessageRequest> = PublishSubject.create()
        private set

    var chatPublishSubject: PublishSubject<Pair<EntityBareJid, Message>> = PublishSubject.create()
        private set

    fun logIn(username: String, password: String) {
        sendRequest(MessageRequest.TYPE_LOG_IN, LogInParams(username, password))
    }

    fun sendChatMessage(username: String, messageBody: String) {
        sendRequest(MessageRequest.TYPE_SEND_CHAT_MESSAGE, SendChatMessageParams(username, messageBody))
    }

    private fun sendRequest(type: Int, params: Any) {
        val request = MessageRequest(type, params)
        requestPublishSubject.onNext(request)
    }
}