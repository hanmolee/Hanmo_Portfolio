package com.portfolio.hanmo.hanmo.Util

import android.view.animation.Animation
import android.opengl.ETC1.getWidth
import android.view.View
import android.view.animation.Transformation


/**
 * Created by hanmo on 2018. 2. 21..
 */
class ResizeWidthAnimation(view: View, width: Int) : Animation() {
    private var mWidth: Int = 0
    private var mStartWidth: Int = 0
    private var mView: View = view

    init {
        mWidth = width
        mStartWidth = view.width
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        val newWidth = mStartWidth + ((mWidth - mStartWidth) * interpolatedTime).toInt()

        mView.layoutParams.width = newWidth
        mView.requestLayout()
    }

    override fun willChangeBounds(): Boolean {
        return true
    }
}