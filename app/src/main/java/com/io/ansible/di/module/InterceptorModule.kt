package com.io.ansible.di.module

import com.io.ansible.BuildConfig
import com.io.ansible.data.preference.Preferences
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by kimsilvozahome on 12/01/2018.
 */
@Module
class InterceptorModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
                .setLevel(
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.BASIC
                )

    }

    @Provides
    @Named("header_interceptor")
    fun provideInterceptor(preferences: Preferences): Interceptor {
        return Interceptor { chain ->
            val oldRequest: Request = chain.request()
            val newRequest: Request = oldRequest.newBuilder()
                    .header("x-jwt", preferences.authTokensPreference.get().apiToken)
                    .method(oldRequest.method(), oldRequest.body())
                    .build()
            chain.proceed(newRequest)
        }
    }
}