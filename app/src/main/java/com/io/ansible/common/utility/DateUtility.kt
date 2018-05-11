package com.io.ansible.common.utility

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by kim.silvoza on 30/04/2018.
 */
class DateUtility {
    companion object {
        fun convertEpochToDate(epoch: Long) : String {
            return convertEpochToString(epoch, "MMMM dd, yyyy")
        }

        fun convertEpochToTime(epoch: Long) : String {
            return convertEpochToString(epoch, "h:mm a")
        }

        private fun convertEpochToString(epoch: Long, pattern: String) : String {
            val sdf = SimpleDateFormat(pattern, Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("Asia/Manila")
            return sdf.format(Date(epoch))
        }
    }
}