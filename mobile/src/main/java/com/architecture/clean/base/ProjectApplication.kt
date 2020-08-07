package com.architecture.clean.base

//import com.squareup.leakcanary.LeakCanary

import androidx.multidex.MultiDexApplication
import com.architecture.clean.BuildConfig
import com.architecture.clean.di.component.ApplicationComponent
import com.architecture.clean.di.component.DaggerApplicationComponent
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