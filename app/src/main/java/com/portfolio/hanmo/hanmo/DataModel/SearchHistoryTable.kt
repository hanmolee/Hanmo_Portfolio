package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by hanmo on 2018. 3. 7..
 */
@RealmClass
open class SearchHistoryTable : RealmObject() {

    @PrimaryKey
    open var id : Int = 0

    open var history_name : String? = null

    open var history_search_time : Long? = 0

    open var techstack_table : RealmList<TechStackTable>? = null

}