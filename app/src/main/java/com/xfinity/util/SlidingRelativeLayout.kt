package com.xfinity.util

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.RelativeLayout

class SlidingRelativeLayout : RelativeLayout {
    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null
    private var yFraction = 0f

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    fun setYFraction(fraction: Float) {

        this.yFraction = fraction

        if (height == 0) {
            if (preDrawListener == null) {
                preDrawListener = ViewTreeObserver.OnPreDrawListener {
                    viewTreeObserver.removeOnPreDrawListener(preDrawListener)
                    setYFraction(yFraction)
                    true
                }
                viewTreeObserver.addOnPreDrawListener(preDrawListener)
            }
            return
        }

        val translationY = height * fraction
        setTranslationY(translationY)
    }

    fun getYFraction(): Float {
        return this.yFraction
    }
}