package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by hanmo on 2018. 2. 3..
 */
@RealmClass
open class Active_Count_Table : RealmObject() {

    @PrimaryKey
    open var id : Int = 0

    open var count : Int? = 0

}