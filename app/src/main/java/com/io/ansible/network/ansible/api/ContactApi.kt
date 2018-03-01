package com.io.ansible.network.ansible.api

import com.io.ansible.network.ansible.model.GetContactsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by kimsilvozahome on 09/02/2018.
 */
interface ContactApi {
    @GET("v1/contacts")
    fun getContacts() : Single<GetContactsResponse>

    @GET("v1/contacts")
    fun getContacts(@Query("after") after: String) : Single<GetContactsResponse>
}