package com.io.ansible.network.ansible.api

import com.io.ansible.network.ansible.model.Profile
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by kimsilvozahome on 06/02/2018.
 */
interface ProfileApi {
    @GET("v1/profile/me")
    fun getProfile() : Single<Profile>
}