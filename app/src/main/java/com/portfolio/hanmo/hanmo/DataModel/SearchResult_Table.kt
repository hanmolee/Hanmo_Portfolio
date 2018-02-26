package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by hanmo on 2018. 2. 22..
 */
@RealmClass
open class SearchResult_Table : RealmObject() {

    @PrimaryKey
    open var id : Int = 0

    open var result : String? = null

    open var search_time : Long? = 0

    var tech_list : RealmList<TechStack_Table>? = null

}
