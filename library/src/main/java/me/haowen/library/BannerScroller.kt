package me.haowen.library

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager


class BannerScroller : Scroller {

    private var mDuration = 300

    constructor(context: Context?) : super(context)

    constructor(context: Context?, interpolator: Interpolator?) : super(context, interpolator)

    constructor(context: Context?, interpolator: Interpolator?, flywheel: Boolean) : super(
        context,
        interpolator,
        flywheel
    )

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    fun setDuration(duration: Int) {
        mDuration = duration
    }

    fun initViewPagerScroll(viewPager: ViewPager) {
        try {
            val mField = ViewPager::class.java.getDeclaredField("mScroller")
            mField.isAccessible = true
            mField.set(viewPager, this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}