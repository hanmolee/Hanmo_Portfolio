package com.portfolio.hanmo.hanmo.Util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.portfolio.hanmo.hanmo.DataModel.TechStackTable
import com.portfolio.hanmo.hanmo.R
import io.realm.*


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

        val tect_list = queryFirst(TechStackTable::class.java)

        when(tect_list) {
            null -> {
                var tech = TechStackTable()
                val name = arrayOf("Kotlin", "AppWidget", "Encryption", "Fabric", "Database", "Material Design", "Reactive X", "API")
                val image = arrayOf("Kotlin", "AppWidget", "Encryption", "Fabric", "Database", "Material Design", "Reactive X", "API")

                for (i in 0 until name.size) {
                    tech.id = i
                    tech.tech_name = name[i]
                    tech.tech_image = image[i]
                    addData(tech)
                }
            }
        }
    }

    fun <T : RealmObject> techStack_queryFirst(clazz: Class<T>, tech_id : Int): T?  {
        return realm!!.where(clazz).equalTo("id", tech_id).findFirst()
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