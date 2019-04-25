package me.haowen.library

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import me.haowen.library.adapter.BannerPagerAdapter
import me.haowen.library.imageloader.ImageLoaderInterface
import me.haowen.library.util.SizeUtil
import me.haowen.library.view.BannerViewPager


class BannerView : FrameLayout {

    var delayTime = 5000L
    var duration: Int = 300
    var isAutoPlay: Boolean = true

    val viewPager: BannerViewPager by lazy { BannerViewPager(context) }

    private val mAdapter: BannerPagerAdapter

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleRes: Int) : super(context, attrs, defStyleRes) {
        mAdapter = BannerPagerAdapter(context)

        // Set ViewPager Scroller duration.
        val mScroller = BannerScroller(context)
        mScroller.duration = duration
        mScroller.initViewPagerScroll(viewPager)

        viewPager.apply {
            clipToPadding = false
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setPadding(SizeUtil.dp2px(15f), 0, SizeUtil.dp2px(15f), 0)
            pageMargin = SizeUtil.dp2px(15f)
            offscreenPageLimit = 3
            adapter = mAdapter
        }

        addView(viewPager)
    }

    var imageList: MutableList<String> = ArrayList()
        set(value) {
            field = value
            mAdapter.data = imageList
            viewPager.setCurrentItem(Integer.MAX_VALUE / 2, false)
            if (isAutoPlay) {
                startAutoPlay()
            }
        }

    var imageLoader: ImageLoaderInterface? = null
        set(value) {
            field = value
            mAdapter.imageLoader = imageLoader
        }

    private val mHandler = WeakHandler()

    fun startAutoPlay() {
        mHandler.removeCallbacks(task)
        mHandler.postDelayed(task, delayTime)
    }

    private val task: Runnable = object : Runnable {
        override fun run() {
            if (imageList.size > 1) {
                viewPager.currentItem = viewPager.currentItem + 1
                mHandler.postDelayed(this, delayTime)
            }
        }
    }

    fun releaseBanner() {
        mHandler.removeCallbacksAndMessages(null)
    }

    fun stopAutoPlay() {
        mHandler.removeCallbacks(task)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (isAutoPlay) {
            val action = ev.action
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE
            ) {
                startAutoPlay()
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}