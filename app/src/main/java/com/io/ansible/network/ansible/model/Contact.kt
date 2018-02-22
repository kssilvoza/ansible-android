package com.io.ansible.network.ansible.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kimsilvozahome on 09/02/2018.
 */
data class Contact(@SerializedName("id") val id : String,
                   @SerializedName("display_name") val displayName : String,
                   @SerializedName("image_url") val imageUrl : String) {}