package com.io.ansible.di.module

import com.io.ansible.BuildConfig
import com.io.ansible.network.ansible.api.AuthApi
import com.io.ansible.network.ansible.api.ContactApi
import com.io.ansible.network.ansible.api.ProfileApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by kimsilvozahome on 12/01/2018.
 */
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideRetrofitBuilder() : Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @Singleton
    fun provideAuthApi(builder: Retrofit.Builder,
                       @Named("client_without_intercept") okHttpClient: OkHttpClient) : AuthApi {
        return builder.client(okHttpClient).build().create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileApi(builder: Retrofit.Builder,
                          @Named("client_with_intercept") okHttpClient: OkHttpClient) : ProfileApi {
        return builder.client(okHttpClient).build().create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideContactApi(builder: Retrofit.Builder,
                          @Named("client_with_intercept") okHttpClient: OkHttpClient) : ContactApi {
        return builder.client(okHttpClient).build().create(ContactApi::class.java)
    }
}