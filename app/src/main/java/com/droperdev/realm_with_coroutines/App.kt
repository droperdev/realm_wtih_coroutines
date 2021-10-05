package com.droperdev.realm_with_coroutines

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config: RealmConfiguration = RealmConfiguration.Builder()
            .name("Test")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}