package com.portfolio.hanmo.hanmo.Fragment

import android.support.v4.app.Fragment
import com.portfolio.hanmo.hanmo.MainActivity

/**
 * Created by hanmo on 2018. 2. 17..
 */
open class BaseFragment : android.app.Fragment() {

    fun replaceFramgment(viewId: Int, fragment: android.app.Fragment) {
        if (activity == null)
            return

        if (activity is MainActivity) {
            val ma = activity as MainActivity
            ma.replaceFragment(fragment)
        }
    }

}