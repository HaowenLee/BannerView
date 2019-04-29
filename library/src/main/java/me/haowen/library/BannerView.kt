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

/**
 * 轮播图控件（包含，ViewPager和IndicatorLayout）
 */
class BannerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), IBannerControl {

    /**
     * 轮播的ViewPager
     */
    private var mViewPager: BannerViewPager = BannerViewPager(context)

    /**
     * 指示器
     */
    var indicatorLayout: IndicatorLayout = IndicatorLayout(context)

    init {
        initIndicatorLayout()

        // 添加 ViewPager 和 IndicatorLayout
        addView(mViewPager)
        addView(indicatorLayout)
    }

    /**
     * 初始化指示器
     */
    private fun initIndicatorLayout() {
        indicatorLayout.unselectedDrawable = context.resources.getDrawable(R.drawable.indicator_item_unselected)
        indicatorLayout.selectedDrawable = context.resources.getDrawable(R.drawable.indicator_item_selected)
        val lp = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        lp.bottomMargin = SizeUtil.dp2px(10f)
        indicatorLayout.layoutParams = lp
    }

    override fun setScrollable(scrollable: Boolean) {
        mViewPager.scrollable = scrollable
    }

    override fun setDuration(duration: Int) {
        mViewPager.duration = duration
    }

    override fun setDelayTime(delayTime: Long) {
        mViewPager.delayTime = delayTime
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        mViewPager.isAutoPlay = autoPlay
    }

    override fun setImageLoader(imageLoader: ImageLoader) {
        mViewPager.imageLoader = imageLoader
    }

    override fun setImageList(imageList: MutableList<Any>) {
        mViewPager.imageList = imageList
        indicatorLayout.setUpWithViewPager(mViewPager, imageList.size)
    }

    override fun getImageList(): MutableList<Any> {
        return mViewPager.imageList
    }

    override fun getViewPager(): BannerViewPager {
        return mViewPager
    }

    override fun startAutoPlay() {
        mViewPager.startAutoPlay()
    }

    override fun stopAutoPlay() {
        mViewPager.stopAutoPlay()
    }

    override fun setOnItemClickedListener(listener: (Int) -> Unit) {
        mViewPager.setOnItemClickedListener(listener)
    }
}