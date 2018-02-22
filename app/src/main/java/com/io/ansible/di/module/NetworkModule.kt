package com.io.ansible.di.module

import com.io.ansible.network.ansible.api.AuthApi
import com.io.ansible.data.preference.Preferences
import dagger.Module
import dagger.Provides
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by kimsilvozahome on 12/01/2018.
 */
@Module
class NetworkModule {
    @Provides
    @Singleton
    fun providesAuthenticator(authApi: AuthApi, preferences: Preferences): Authenticator {
        return Authenticator { route, response ->
            if (responseCount(response) >= MAX_REFRESH_COUNT) {
                null
            }

            if (response.request().url().toString().contains(REFRESH_URL)) {
                response.request()
            }

            val call = authApi.refreshToken(preferences.authTokensPreference.get().apiToken)
            val authResponse = call.execute().body()
            if (authResponse != null) {
                preferences.authTokensPreference.set(authResponse)
                response.request().newBuilder()
                        .header("x-jwt", authResponse.apiToken)
                        .build()
            } else {
                null
            }
        }
    }

    @Provides
    @Named("client_without_intercept")
    fun providesLoggingOkHttpClientWithoutIntercept(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Named("client_with_intercept")
    fun providesLoggingOkHttpClientWithIntercept(authenticator: Authenticator, httpLoggingInterceptor: HttpLoggingInterceptor, @Named("header_interceptor") interceptor: Interceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
                .authenticator(authenticator)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(interceptor).build()
    }

    private fun responseCount(response: Response?): Int {
        var currResponse: Response? = response
        var result = 1

        while (currResponse?.priorResponse() != null) {
            result = result + 1
            currResponse = currResponse.priorResponse()
        }
        return result
    }

    companion object {
        private val MAX_REFRESH_COUNT = 3
        private val REFRESH_URL = "/v1/auth/token?type=refresh"
    }
}