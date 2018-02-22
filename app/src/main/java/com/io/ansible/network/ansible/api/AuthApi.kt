package com.io.ansible.network.ansible.api

import com.io.ansible.BuildConfig
import com.io.ansible.network.ansible.model.AuthTokens
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by kimsilvozahome on 10/01/2018.
 */
interface AuthApi {
    @Headers("Authorization: " + BuildConfig.AUTHORIZATION)
    @GET("/v1/auth/token?type=facebook")
    fun exchangeFacebookToken(@Query("access_token") accessToken : String) : Single<AuthTokens>

    @Headers("Authorization: " + BuildConfig.AUTHORIZATION)
    @GET("/v1/auth/token?type=twitter")
    fun exchangeTwitterToken(@Query("access_token") accessToken : String, @Query("access_token_secret") accessTokenSecret : String) : Single<AuthTokens>

    @Headers("Authorization: " + BuildConfig.AUTHORIZATION)
    @GET("/v1/auth/token?type=refresh")
    fun refreshToken(@Query("token") token : String) : Call<AuthTokens>
}