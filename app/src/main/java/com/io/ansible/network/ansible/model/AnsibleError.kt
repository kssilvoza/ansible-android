package com.io.ansible.network.ansible.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by kimsilvozahome on 17/01/2018.
 */
data class AnsibleError(
        @Expose @SerializedName("code") val code: Int?,
        @Expose @SerializedName("message") val message: String?,
        @Expose @SerializedName("user_spiel") val userSpiel: String?,
        var kind: Kind) {
    enum class Kind {
        HTTP,
        NETWORK,
        UNEXPECTED
    }
}