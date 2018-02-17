package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by hanmo on 2018. 2. 17..
 */
@RealmClass
open class Admin_Table : RealmObject() {

    @PrimaryKey
    open var id : Int = 0

    open var admin_id : String? = null

    open var admin_password : String? = null
}