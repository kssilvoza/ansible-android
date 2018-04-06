package com.io.ansible.messaging

/**
 * Created by kimsilvozahome on 05/04/2018.
 */
data class MessageRequest(
        val type: Int,
        val payload: Any) {
    companion object {
        const val TYPE_LOG_IN = 1
        const val TYPE_SEND_CHAT_MESSAGE = 2
    }
}