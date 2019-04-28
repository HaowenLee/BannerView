package me.haowen.library

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import me.haowen.library.imageloader.ImageLoader
import me.haowen.library.util.SizeUtil
import me.haowen.library.view.BannerViewPager
import me.haowen.library.view.IndicatorLayout


class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    /**
     * 是否可以滑动
     */
    var scrollable = true
        set(value) {
            field = value
            viewPager.scrollable = value
        }
    /**
     * ViewPager切换的时长
     */
    var duration: Int = 300
        set(value) {
            if (field == value) {
                return
            }
            field = value
            viewPager.duration = value
        }
    /**
     * 页面停留时间
     */
    var delayTime = 5000L
        set(value) {
            field = value
            viewPager.delayTime = value
        }
    /**
     * 是否自动轮播
     */
    var isAutoPlay: Boolean = true
        set(value) {
            field = value
            viewPager.isAutoPlay = value
        }
    /**
     * 图片轮播
     */
    var imageLoader: ImageLoader? = null
        set(value) {
            field = value
            viewPager.imageLoader = value
        }
    /**
     * 数据
     */
    var imageList: MutableList<Any> = ArrayList()
        set(value) {
            field = value
            viewPager.imageList = value
            indicatorLayout.setUpWithViewPager(viewPager, value.size)
        }

    /**
     * 轮播的ViewPager
     */
    var viewPager: BannerViewPager = BannerViewPager(context)
    /**
     * 指示器
     */
    var indicatorLayout: IndicatorLayout = IndicatorLayout(context)

    init {
        indicatorLayout.unselectedDrawable = context.resources.getDrawable(R.drawable.indicator_item_unselected)
        indicatorLayout.selectedDrawable = context.resources.getDrawable(R.drawable.indicator_item_selected)
        val lp = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        lp.bottomMargin = SizeUtil.dp2px(10f)
        addView(viewPager)
        addView(indicatorLayout, lp)
    }
}