package com.portfolio.hanmo.hanmo.Util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.portfolio.hanmo.hanmo.Activity.AdminActivity
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.DataModel.Admin_Table
import com.portfolio.hanmo.hanmo.DataModel.SearchResult_Table
import com.portfolio.hanmo.hanmo.DataModel.TechStack_Table
import com.portfolio.hanmo.hanmo.MainActivity
import com.portfolio.hanmo.hanmo.MainActivity.Companion.admin
import com.portfolio.hanmo.hanmo.R
import io.realm.*
import org.jetbrains.anko.toast
import io.realm.RealmList



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
        val tect_list = queryFirst(TechStack_Table::class.java)

        when(count) {
            null -> {
                var count_table = Active_Count_Table()
                count_table.id = 1
                count_table.count = 1
                val adminList = RealmList<Admin_Table>()
                val admin_result = queryAll(Admin_Table::class.java)
                when(admin_result.size) {
                    0 -> {
                        val admin = Admin_Table()
                        adminList.add(admin)
                    }
                }
                count_table.admin = adminList
                realm!!.beginTransaction()
                realm!!.copyToRealmOrUpdate(count_table)
                realm!!.commitTransaction()
            }
        }
        when(tect_list) {
            null -> {
                var tech = TechStack_Table()
                val name = arrayOf("FCM PUSH", "AppWidget", "Encryption", "tech 04", "tech 05", "tech 06")

                for (i in 0..5) {
                    tech.id = i
                    tech.tech_name = name[i]
                    addData(tech)
                }
            }
        }
    }

    fun <T : RealmObject> selectSearchResult(clazz: Class<T>): RealmResults<T> {
        return realm!!.where(clazz).findAll().sort("search_time", Sort.DESCENDING).where().distinct("result")
    }

    fun insertSearchResult(name: String) {
        val currentIdNum = realm!!.where(SearchResult_Table::class.java!!).max("id")
        val nextId: Int
        nextId = when(currentIdNum){
            null -> {
                1
            }
            else -> {
                currentIdNum!!.toInt() + 1
            }
        }
        val result = SearchResult_Table()
        result.id = nextId
        result.result = name
        result.search_time = System.currentTimeMillis()
        /*var test = queryAll(TechStack_Table::class.java)
        val list = RealmList<TechStack_Table>()
        test.forEach {
            val techlist = TechStack_Table()
            techlist.id = it.id
            techlist.tech_name = it.tech_name
            list.add(techlist)
        }
        result.tech_list = list*/
        val admin_result = queryAll(Admin_Table::class.java)
        val adminList = RealmList<Admin_Table>()
        when(admin_result.size) {
            0 -> {
                val admin = Admin_Table()
                adminList.add(admin)
            }
            else -> {
                admin_result.forEach {
                    val admin = Admin_Table()
                    admin.id = it.id
                    admin.admin_id = it.admin_id
                    admin.admin_password = it.admin_password
                    adminList.add(admin)
                }
            }
        }
        result.admin = adminList

        realm!!.beginTransaction()
        realm!!.copyToRealmOrUpdate(result)
        realm!!.commitTransaction()

    }

    fun updateSearchResult(result: String) {
        val toEdit = realm!!.where(Admin_Table::class.java).equalTo("history.result", result).findAll()
        when(toEdit.size){
            0 -> {  }
            else -> {
                toEdit.forEach {
                    realm!!.beginTransaction()
                    it.history?.forEach {
                        it.search_time = System.currentTimeMillis()
                    }
                    realm!!.commitTransaction()
                }
            }
        }
    }

    fun updateActiveCount() {

        val toEdit = queryFirst(Active_Count_Table::class.java)
        var count = toEdit!!.count
        realm!!.beginTransaction()
        toEdit!!.count = count!! + 1
        realm!!.commitTransaction()

    }

    fun adminLogin(context: AdminActivity, id: String, pwd: String) {
        val toEdit = realm!!.where(Admin_Table::class.java).equalTo("admin_id", id).findFirst()
        when(toEdit) {
            null -> {
                //아이디가 존재하지 않습니다
                context.toast("아이디가 존재하지 않습니다.")
            }
            else -> {
                when {
                    toEdit!!.admin_password.equals(pwd) -> {
                        //로그인 가능
                        admin = 1
                        context.setResult(100)
                        context.finish()
                    }
                    else -> {
                        //비밀번호가 틀렸습니다
                        context.toast("비밀번호가 틀렸습니다")
                    }
                }
            }
        }
    }

    fun adminCreate(id: String, pwd: String) {
        val currentIdNum = realm!!.where(Admin_Table::class.java!!).max("id")
        val nextId: Int
        nextId = when(currentIdNum){
            null -> {
                1
            }
            else -> {
                currentIdNum!!.toInt() + 1
            }
        }

        var admin = Admin_Table()
        admin.id = nextId
        admin.admin_id = id
        admin.admin_password = pwd
        /*val count = queryAll(Active_Count_Table::class.java)
        val history = queryAll(SearchResult_Table::class.java)
        val count_list = RealmList<Active_Count_Table>()
        val history_list = RealmList<SearchResult_Table>()
        when(count.size) {
            0 -> {
                Log.e("count", "count is null")
            }
            else -> {
                count.forEach {
                    val cnt = Active_Count_Table()
                    cnt.id = it.id
                    cnt.count = it.count
                    count_list.add(cnt)
                }
            }
        }
        when(history.size) {
            0 -> { Log.e("history", "history is null") }
            else -> {
                history.forEach {
                    val hst = SearchResult_Table()
                    hst.id = it.id
                    hst.result = it.result
                    hst.search_time = it.search_time
                    Log.e("history value", "id : " + it.id + ", result : " + it.result)
                    history_list.add(hst)
                }
            }
        }

        admin.history = history_list
        admin.count = count_list
        realm!!.beginTransaction()
        realm!!.copyToRealmOrUpdate(admin)
        realm!!.commitTransaction()*/
        addData(admin)

    }

    fun <T : RealmObject> testQuery(clazz: Class<T>) : T?{
        return realm!!.where(clazz).equalTo("admin_id", MainActivity.admin_id).findFirst()
    }

    fun deleteStackList(id: Int) {
        val toEdit = realm!!.where(TechStack_Table::class.java).equalTo("id", id).findFirst()

        realm!!.beginTransaction()
        toEdit!!.deleteFromRealm()
        realm!!.commitTransaction()

    }

    fun testDelete(name: String) {
        val toEdit = realm!!.where(SearchResult_Table::class.java).equalTo("result", name).findAll()

        realm!!.beginTransaction()
        toEdit!!.deleteAllFromRealm()
        realm!!.commitTransaction()

    }

    fun testupdate(name: String) {
        val toEdit = realm!!.where(SearchResult_Table::class.java).equalTo("result", name).findAll()

        realm!!.beginTransaction()
        toEdit.forEach {
            it.result = "이름을 바꿔라"
        }
        realm!!.commitTransaction()

    }

    fun <T : RealmObject> queryResults(clazz: Class<T>, result: String): RealmResults<T> {
        return realm!!.where(clazz).contains("tech_name", result).findAll()
    }

    fun <T : RealmObject>test(clazz: Class<T>) : RealmResults<T> {
        return realm!!.where(clazz).equalTo("search_result.result", "tech 04").and().equalTo("tech_name", "tech 04").findAll()
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