package com.io.ansible.ui.messagethread.model

import com.io.ansible.data.database.model.MessageWithContact

/**
 * Created by kim.silvoza on 30/04/2018.
 */
class MessageThreadItem {
    var isDateShown = false
    var isContactShown = false

    lateinit var messageWithContact : MessageWithContact

    override fun toString(): String {
        return "MessageThreadItem(isDateShown=$isDateShown, isContactShown=$isContactShown, messageWithContact=$messageWithContact)"
    }
}