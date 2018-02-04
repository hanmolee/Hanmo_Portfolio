package com.portfolio.hanmo.hanmo.Util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.R
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Created by hanmo on 2018. 2. 3..
 */
class RealmHelper private constructor() {

    val CREATE_REALM = "Create Realm"

    var realm: Realm? = null
        private set

    init {
        try {

            realm = Realm.getDefaultInstance()

        } catch (e: Exception) {
            val config = RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build()
            realm = Realm.getInstance(config)
        }
    }

    fun checkFirstStartApp(context : Context) {
        val pref = context.getSharedPreferences(context.resources.getString(R.string.createRealm), Context.MODE_PRIVATE)
        val checkedRealmObject = pref.getBoolean(context.resources.getString(R.string.createRealm), false)
        when(checkedRealmObject){

            false -> {createRealmObject(pref, context)}
            true -> { Log.e("기존사용자", "기존사용자") }

        }
    }

    private fun createRealmObject(pref: SharedPreferences, context: Context) {

        val editor = pref.edit()
        editor.putBoolean(context.resources.getString(R.string.createRealm), true)
        editor.commit()

        val count = queryFirst(Active_Count_Table::class.java)
        when(count){
            null -> {
                var count_table = Active_Count_Table()
                count_table.id = 1
                count_table.count = 1
                addData(count_table)
            }
            else -> { Log.d(CREATE_REALM, "active count table init fail") }
        }

    }

    fun updateActiveCount() {

        val toEdit = queryFirst(Active_Count_Table::class.java)
        var count = toEdit!!.count
        realm!!.beginTransaction()
        toEdit!!.count = count!! + 1
        realm!!.commitTransaction()

    }

    //Insert To Realm
    fun <T : RealmObject> addData(data: T) {

        realm!!.beginTransaction()
        realm!!.copyToRealm(data)
        realm!!.commitTransaction()
    }

    fun <T : RealmObject> queryFirst(clazz: Class<T>): T? {
        return realm!!.where(clazz).findFirst()
    }


    fun <T : RealmObject> queryAll(clazz: Class<T>): RealmResults<T> {
        return realm!!.where(clazz).findAll()
    }


    fun <T : RealmObject> delete(data: T) {
        realm!!.beginTransaction()
        data.deleteFromRealm()
        realm!!.commitTransaction()
    }

    fun <T : RealmObject> deleteAll(clazz: Class<T>) {
        val results = realm!!.where(clazz).findAll()
        realm!!.executeTransaction { results.deleteAllFromRealm() }
    }


    companion object {

        private var INSTANCE: RealmHelper? = null

        val instance: RealmHelper
            get() {
                if (INSTANCE == null) {
                    INSTANCE = RealmHelper()
                }
                return INSTANCE as RealmHelper
            }
    }





}