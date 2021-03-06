package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by hanmo on 2018. 2. 4..
 */
@RealmClass
open class TechStackTable : RealmObject() {

    @PrimaryKey
    open var id : Int = 0

    open var tech_name : String? = null

    open var tech_image : String? = null

    @LinkingObjects("techstack_table")
    val history : RealmResults<SearchHistoryTable>? = null

}