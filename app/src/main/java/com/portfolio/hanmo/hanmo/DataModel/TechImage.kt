package com.portfolio.hanmo.hanmo.DataModel

import android.graphics.drawable.Drawable
import com.portfolio.hanmo.hanmo.R

/**
 * Created by hanmo on 2018. 3. 6..
 */
object TechImage {
    fun techList() : ArrayList<Int> {
        val list = ArrayList<Int>()
        list.add(R.drawable.kotlin)
        list.add(R.drawable.fabric)
        list.add(R.drawable.encrypted)
        list.add(R.drawable.reactivex)
        list.add(R.drawable.one)
        list.add(R.drawable.two)
        list.add(R.drawable.appwidget)
        list.add(R.drawable.reactivex)

        return list
    }
}