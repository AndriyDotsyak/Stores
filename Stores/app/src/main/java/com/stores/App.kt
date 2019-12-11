package com.stores

import android.app.Activity
import android.app.Application
import com.stores.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityInjector

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this@App)
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this@App)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder().build()
        )
    }
}