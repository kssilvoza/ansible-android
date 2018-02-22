package com.io.ansible.network.ansible.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kimsilvozahome on 10/01/2018.
 */
data class AuthTokens(
        @SerializedName("api_token") val apiToken : String,
        @SerializedName("api_refresh_token") val apiRefreshToken : String,
        @SerializedName("messaging_token") val messagingToken : String) {
}