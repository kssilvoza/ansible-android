package com.io.ansible.data.preference

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.google.gson.Gson
import com.io.ansible.common.rxpreferences.GsonPreferenceConverter
import com.io.ansible.common.singleton.SingletonHolder
import com.io.ansible.network.ansible.model.AuthTokens
import com.io.ansible.network.ansible.model.Profile

/**
 * Created by kimsilvozahome on 12/01/2018.
 */
class Preferences private constructor(context: Context) {
    var authTokensPreference: Preference<AuthTokens>
        private set
    var profilePreference: Preference<Profile>
        private set

    init {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
        val rxSharedPreferences = RxSharedPreferences.create(sharedPreferences)

        val gson = Gson()
        authTokensPreference = rxSharedPreferences.getObject(
                KEY_AUTH_TOKENS,
                AuthTokens("", "", ""),
                GsonPreferenceConverter<AuthTokens>(gson, AuthTokens::class.java)
        )
        profilePreference = rxSharedPreferences.getObject(
                KEY_PROFILE,
                Profile("", "", "", "", "", ""),
                GsonPreferenceConverter<Profile>(gson, Profile::class.java)
        )
    }

    companion object : SingletonHolder<Preferences, Context>(::Preferences){
        private const val KEY_AUTH_TOKENS = "key_auth_tokens"
        private const val KEY_PROFILE = "key_auth_profile"
    }
}