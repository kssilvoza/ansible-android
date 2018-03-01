package com.io.ansible.network.ansible.model

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by kimsilvozahome on 17/01/2018.
 */
data class AnsibleError(
        @Expose @SerializedName("code") var code: Int?,
        @Expose @SerializedName("message") var message: String?,
        @Expose @SerializedName("user_spiel") var userSpiel: String?,
        var kind: Kind) {
    enum class Kind {
        HTTP,
        NETWORK,
        UNEXPECTED
    }

    constructor(throwable: Throwable): this(null, null, null, Kind.UNEXPECTED) {
        when (throwable) {
            is HttpException -> {
                val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
                try {
                    val ansibleError = gson.fromJson(throwable.response().errorBody().toString(), AnsibleError::class.java)
                    code = ansibleError.code
                    message = ansibleError.message
                    userSpiel = ansibleError.userSpiel
                    kind = Kind.HTTP
                } catch (e: Exception) {
                    kind = Kind.UNEXPECTED
                }
            }
            is IOException -> {
                kind = Kind.NETWORK
            }
            else -> {
                kind = Kind.UNEXPECTED
            }
        }
    }
}