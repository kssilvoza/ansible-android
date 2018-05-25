package com.io.ansible.network.ansible.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kimsilvozahome on 09/02/2018.
 */
data class GetContactsResponse (@SerializedName("data") val contacts: List<Contact>,
                                @SerializedName("pagination") val pagination: Pagination) {

}