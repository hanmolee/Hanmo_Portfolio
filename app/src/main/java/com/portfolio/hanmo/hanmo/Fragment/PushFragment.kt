package com.portfolio.hanmo.hanmo.Fragment

import android.app.Fragment
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.portfolio.hanmo.hanmo.R

/**
 * Created by hanmo on 2018. 2. 17..
 */
class PushFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater!!.inflate(R.layout.fragment_push, container, false)

        rootView.isFocusableInTouchMode = true
        rootView.requestFocus()
        rootView.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                val viewPager = Fragment_Pager()
                replaceFramgment(R.id.content_frame, viewPager)
                return@OnKeyListener true
            }
            false
        })

        return rootView
    }
}