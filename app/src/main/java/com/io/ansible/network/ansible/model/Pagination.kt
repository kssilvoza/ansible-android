package com.io.ansible.network.ansible.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kimsilvozahome on 09/02/2018.
 */
data class Pagination(@SerializedName("before") val before: String,
                      @SerializedName("after") val after: String) {
}