package com.portfolio.hanmo.hanmo.Service

import android.app.Application
import com.portfolio.hanmo.hanmo.Util.RealmHelper
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by hanmo on 2018. 2. 1..
 */
class HanmoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        realmInit()
    }

    private fun realmInit() {
        Realm.init(this)

        val config = RealmConfiguration.Builder()
                .name("hanmo.realm")
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)

        RealmHelper.instance.checkFirstStartApp(this)
    }
}