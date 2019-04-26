package me.haowen.library

import android.content.Context
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager


class BannerScroller(context: Context?) : Scroller(context) {

    /**
     * ViewPager切换的时长
     */
    private var mDuration = 300

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)
    }

    fun setDuration(duration: Int) {
        mDuration = duration
    }

    /**
     * 反射设置ViewPager的Scroller
     */
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