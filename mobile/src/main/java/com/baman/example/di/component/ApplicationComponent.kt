package com.baman.example.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.baman.data.network.ApiService
import com.baman.data.network.AuthorizedApiService
import com.baman.data.network.di.ApiServiceModule
import com.baman.data.repository.datasource.TokenDataStore
import com.baman.example.di.module.ApplicationModule
import com.baman.example.view.Navigator
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ApiServiceModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance app : Application
        ) : ApplicationComponent
    }

    fun getApplication(): Application
    fun getApplicationContext(): Context
    fun getSharedPreferences(): SharedPreferences

    fun authorizedApiService(): AuthorizedApiService
    fun apiService(): ApiService

    @Named("retrofit")
    fun retrofit() : Retrofit
    @Named("authorizedRetrofit")
    fun authorizedRetrofit() : Retrofit

    @Named("okHttpClient")
    fun okHttpClient() : OkHttpClient
    @Named("authorizedOkHttpClient")
    fun authorizedOkHttp() : OkHttpClient

    fun gsonConverter() : GsonConverterFactory

    fun tokenDataStore(): TokenDataStore

    fun navigator() : Navigator

}
