package com.io.ansible.common.rxpreferences

import android.content.SharedPreferences

/**
 * Created by kimsilvozahome on 01/02/2018.
 */
interface Adapter<T> {
    operator fun get(key: String, preferences: SharedPreferences): T

    operator fun set(key: String, value: T, editor: SharedPreferences.Editor)
}