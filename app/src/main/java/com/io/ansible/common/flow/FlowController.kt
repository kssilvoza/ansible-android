package com.io.ansible.common.flow

import android.app.Activity
import android.content.Intent
import com.io.ansible.ui.home.view.activity.HomeActivity

/**
 * Created by kimsilvozahome on 16/01/2018.
 */
class FlowController {
    fun flowToHomeActivity(activity: Activity) {
        val intent = Intent(activity.applicationContext, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)
        activity.finish()
    }
}