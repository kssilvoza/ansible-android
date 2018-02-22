package com.io.ansible.network.ansible.api

import com.io.ansible.network.ansible.model.GetContactsResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by kimsilvozahome on 09/02/2018.
 */
interface ContactApi {
    @GET("v1/contacts")
    fun getContacts() : Single<GetContactsResponse>
}