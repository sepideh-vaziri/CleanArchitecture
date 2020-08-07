package com.baman.example.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.baman.example.BuildConfig
import dagger.Module
import dagger.Provides

@Module
object ApplicationModule {

    @Provides
    @JvmStatic
    fun provideApplicationContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @JvmStatic
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

}