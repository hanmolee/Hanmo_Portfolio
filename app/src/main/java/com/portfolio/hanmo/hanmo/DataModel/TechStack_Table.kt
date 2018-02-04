package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by hanmo on 2018. 2. 4..
 */
@RealmClass
open class TechStack_Table : RealmObject() {

    @PrimaryKey
    open var id : Int = 0

    open var tech_name : String? = null
}