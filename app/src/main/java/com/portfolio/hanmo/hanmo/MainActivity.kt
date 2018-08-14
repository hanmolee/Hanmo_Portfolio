package com.portfolio.hanmo.hanmo

import android.app.Activity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.portfolio.hanmo.hanmo.Fragment.Fragment_Pager


/**
 * Created by hanmo on 2018. 2. 3..
 */
class MainActivity : FragmentActivity() {

    companion object {
        var admin = 0
        var admin_id = ""
    }

    private var isFirstBackClicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment_Pager = Fragment_Pager()
        replaceContentFragment(R.id.content_frame, fragment_Pager)

    }

    fun replaceFragment(fragment: android.app.Fragment) {
        replaceContentFragment(R.id.content_frame, fragment)
    }

    fun Activity.replaceContentFragment(@IdRes frameId: Int, fragment: android.app.Fragment){
        fragmentManager.beginTransaction().replace(frameId, fragment).commit()
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            if (isFirstBackClicked) {
                Toast.makeText(this, "한번 더 누르면 종료", Toast.LENGTH_SHORT).show()
                isFirstBackClicked = false
                return
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}