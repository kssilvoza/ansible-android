package com.io.ansible.data.store

import android.util.Log
import com.io.ansible.data.database.dao.MessageDao
import com.io.ansible.data.database.entity.MessageEntity
import com.io.ansible.message.MessageBus
import io.reactivex.Flowable
import kotlinx.coroutines.experimental.async
import org.jivesoftware.smack.packet.Message
import org.jxmpp.jid.EntityBareJid

/**
 * Created by kimsilvozahome on 09/03/2018.
 */
class MessageStore(private val messageDao: MessageDao) {
    init {
        MessageBus.chatPublishSubject.subscribe {
            Log.d("MessageStore", "onNext")
            onChatReceived(it.first, it.second)
        }
    }

    fun observeMessages(threadId: String): Flowable<List<MessageEntity>> {
        return messageDao.get(threadId)
    }

    fun sendMessage(senderId: String, receiverId: String, messageBody: String) {
        MessageBus.sendChatMessage(receiverId, messageBody)

        // TODO - Add from variable
        val messageEntity = MessageEntity(0, receiverId, senderId, System.currentTimeMillis(), MessageEntity.DIRECTION_OUTGOING, messageBody)
        insertMessage(messageEntity)
    }

    private fun onChatReceived(senderJid: EntityBareJid, message: Message) {
        Log.d("MessageStore", "onChatReceived: ${message.body}")
        val senderId = senderJid.asEntityBareJidString().split("@")[0]
        val messageEntity = MessageEntity(0, senderId, senderId, System.currentTimeMillis(), MessageEntity.DIRECTION_INCOMING, message.body)
        insertMessage(messageEntity)
    }

    private fun insertMessage(messageEntity: MessageEntity) {
        async {
            Log.d("MessageStore", "Insert Message: ${messageEntity.content}")
            messageDao.insert(messageEntity)
        }
    }
}