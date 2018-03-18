package com.portfolio.hanmo.hanmo.DataModel

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by hanmo on 2018. 3. 18..
 */
@RealmClass
open class UsersTable : RealmObject() {

    @PrimaryKey
    open var id : Int = 0

    open var name : String? = null

    open var userId : String? = null

    open var pwd : String? = null

    open var createdAt : Long? = null

}