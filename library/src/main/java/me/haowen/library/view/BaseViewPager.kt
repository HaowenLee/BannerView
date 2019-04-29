package me.haowen.library.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

abstract class BaseViewPager : ViewPager {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    abstract fun getRealPosition(position: Int): Int
}