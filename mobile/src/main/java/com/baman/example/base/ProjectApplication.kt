package com.baman.example.base

//import com.squareup.leakcanary.LeakCanary

import androidx.multidex.MultiDexApplication
import com.baman.example.BuildConfig
import com.baman.example.di.component.ApplicationComponent
import com.baman.example.di.component.DaggerApplicationComponent
import com.baman.example.di.module.ApplicationModule
import timber.log.Timber


class ProjectApplication : MultiDexApplication() {

    private lateinit var mApplicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()

//        if (!LeakCanary.isInAnalyzerProcess(this)) {
//            val refWatcher = LeakCanary.install(this)
//        }

        mApplicationComponent = DaggerApplicationComponent
            .factory()
            .create(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

    //**********************************************************************************************
    /**
     * Return [ApplicationComponent]
     */
    fun getApplicationComponent(): ApplicationComponent {
        return mApplicationComponent
    }

}