package com.osman.downloadtask

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Timber.plant(Timber.DebugTree())
        RxJavaPlugins.setErrorHandler { }
    }
}