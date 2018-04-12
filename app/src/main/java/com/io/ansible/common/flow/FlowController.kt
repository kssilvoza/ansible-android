package com.io.ansible.common.flow

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.io.ansible.ui.home.view.activity.HomeActivity
import com.io.ansible.ui.messagethread.view.activity.MessageThreadActivity

/**
 * Created by kimsilvozahome on 16/01/2018.
 */
class FlowController {
    fun flowToHomeActivity(activity: FragmentActivity?) {
        if (activity != null) {
            val intent = Intent(activity.applicationContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
            activity.finish()
        }
    }

    fun flowToMessageThreadActivity(activity: FragmentActivity?, id: String) {
        if (activity != null) {
            val intent = Intent(activity.applicationContext, MessageThreadActivity::class.java)
            intent.putExtra(MessageThreadActivity.INTENT_EXTRA_ID, id)
            activity.startActivity(intent)
        }
    }
}