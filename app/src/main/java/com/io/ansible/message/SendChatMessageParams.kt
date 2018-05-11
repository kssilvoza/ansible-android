package com.io.ansible.message

/**
 * Created by kim.silvoza on 12/04/2018.
 */
data class SendChatMessageParams(
        val username: String,
        val messageBody: String) {
}