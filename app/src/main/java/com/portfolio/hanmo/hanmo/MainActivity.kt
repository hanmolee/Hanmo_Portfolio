package com.portfolio.hanmo.hanmo

import android.app.Activity
import android.os.Bundle
import android.support.annotation.IdRes
import com.portfolio.hanmo.hanmo.DataModel.Active_Count_Table
import com.portfolio.hanmo.hanmo.Fragment.FirstViewFragment
import com.portfolio.hanmo.hanmo.Fragment.Fragment_Pager
import com.portfolio.hanmo.hanmo.Util.RealmHelper

/**
 * Created by hanmo on 2018. 2. 3..
 */
class MainActivity : Activity() {

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val run_count = RealmHelper.instance.queryFirst(Active_Count_Table::class.java)
        when{
            run_count != null -> {
                count = run_count.count!!
                RealmHelper.instance.updateActiveCount()
            }
        }

        val fragment_Pager = Fragment_Pager()
        replaceContentFragment(R.id.content_frame, fragment_Pager)

    }

    fun Activity.replaceContentFragment(@IdRes frameId: Int, fragment: android.app.Fragment){
        fragmentManager.beginTransaction().replace(frameId, fragment).commit()
    }


}