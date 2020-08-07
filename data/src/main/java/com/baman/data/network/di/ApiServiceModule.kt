package com.baman.data.network.di

import com.baman.data.BuildConfig
import com.baman.data.network.ApiService
import com.baman.data.network.AuthorizationInterceptor
import com.baman.data.network.AuthorizedApiService
import com.baman.data.network.ServerUrl
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object ApiServiceModule {

    internal const val REQUEST_TIME_OUT = 120L //Seconds

    @Singleton
    @Provides
    @JvmStatic
    fun apiService(
        @Named("retrofit")  retrofit: Retrofit
    ) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun authorizedApiService(
        @Named("authorizedRetrofit") retrofit: Retrofit
    ) : AuthorizedApiService {
        return retrofit.create(AuthorizedApiService::class.java)
    }

    @Singleton
    @Provides
    @JvmStatic
    @Named("retrofit")
    fun provideRetrofit(
        @Named("okHttpClient") client : Lazy<OkHttpClient>,
        gsonConverterFactory: GsonConverterFactory
    ) : Retrofit {

        return Retrofit.Builder()
            .callFactory { request -> client.get().newCall(request) }
            .baseUrl(ServerUrl.API_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    }

    @Singleton
    @Provides
    @JvmStatic
    @Named("authorizedRetrofit")
    fun provideAuthorizedRetrofit(
        @Named("authorizedOkHttpClient") client : Lazy<OkHttpClient>,
        gsonConverterFactory: GsonConverterFactory
    ) : Retrofit {

        return Retrofit.Builder()
            .callFactory { request -> client.get().newCall(request) }
            .baseUrl(ServerUrl.API_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()

    }

    @Singleton
    @Provides
    @JvmStatic
    @Named("okHttpClient")
    fun provideOkHttpClient() : OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)

        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    @JvmStatic
    @Named("authorizedOkHttpClient")
    fun provideAuthorizedOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor
    ) : OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        httpClientBuilder.addInterceptor(authorizationInterceptor::interceptor)
        httpClientBuilder.authenticator(authorizationInterceptor)

        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        httpClientBuilder.readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)

        return httpClientBuilder.build()
    }

    @Provides
    @JvmStatic
    fun provideGsonConverter() : GsonConverterFactory = GsonConverterFactory.create()

}