package com.io.ansible.network.ansible.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kimsilvozahome on 06/02/2018.
 */
data class Profile(@SerializedName("id") val id : String,
                   @SerializedName("first_name") val firstName : String,
                   @SerializedName("last_name") val lastName : String,
                   @SerializedName("email") val email : String,
                   @SerializedName("image_url") val imageUrl : String,
                   @SerializedName("msisdn") val msisdn : String) {
    fun getName() : String {
        return firstName + " " + lastName
    }
}