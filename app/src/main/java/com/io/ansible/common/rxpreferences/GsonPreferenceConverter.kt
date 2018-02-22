package com.io.ansible.common.rxpreferences

import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.Preference
import com.google.gson.Gson

/**
 * Created by kimsilvozahome on 01/02/2018.
 */
class GsonPreferenceConverter<T>(gson : Gson, clazz: Class<T>) : Preference.Converter<T> {
    private val mGson = gson
    private val mClass = clazz

    override fun deserialize(serialized: String): T {
        return mGson.fromJson(serialized, mClass)
    }

    override fun serialize(value: T): String {
        return mGson.toJson(value, mClass)
    }
}