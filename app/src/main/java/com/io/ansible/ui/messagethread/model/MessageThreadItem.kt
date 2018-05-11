package com.io.ansible.ui.messagethread.model

/**
 * Created by kim.silvoza on 30/04/2018.
 */
class MessageThreadItem {
    var id : Long = 0
    var isDateShown = false
    var isContactShown = false
    var date = ""
    var contactImageUrl = ""
    var name = ""
    var content = ""
    var time = ""

    override fun toString(): String {
        return "MessageThreadItem(id=$id, isDateShown=$isDateShown, isContactShown=$isContactShown, date='$date', contactImageUrl='$contactImageUrl', name='$name', content='$content', time='$time')"
    }
}