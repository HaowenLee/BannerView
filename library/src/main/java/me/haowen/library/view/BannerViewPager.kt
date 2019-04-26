package me.haowen.library.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.viewpager.widget.ViewPager
import me.haowen.library.BannerScroller
import me.haowen.library.WeakHandler
import me.haowen.library.adapter.BannerPagerAdapter
import me.haowen.library.imageloader.ImageLoaderInterface
import me.haowen.library.util.SizeUtil

class BannerViewPager : ViewPager {

    /**
     * 是否可以滑动
     */
    var scrollable = true
    /**
     * ViewPager切换的时长
     */
    var duration: Int = 300
        set(value) {
            if (field == value) {
                return
            }
            field = value
            mScroller.duration = duration
        }
    /**
     * 页面停留时间
     */
    var delayTime = 5000L
    /**
     * 是否自动轮播
     */
    var isAutoPlay: Boolean = true
    /**
     * ViewPager数据适配器
     */
    private val mAdapter: BannerPagerAdapter

    private val mScroller: BannerScroller

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mScroller = BannerScroller(context)
        mScroller.initViewPagerScroll(this)

        mAdapter = BannerPagerAdapter(context)

        clipToPadding = false
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setPadding(SizeUtil.dp2px(15f), 0, SizeUtil.dp2px(15f), 0)
        pageMargin = SizeUtil.dp2px(15f)
        offscreenPageLimit = 3
        adapter = mAdapter
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (this.scrollable) {
            if (currentItem == 0 && childCount == 0) {
                false
            } else {
                super.onTouchEvent(ev)
            }
        } else {
            false
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (this.scrollable) {
            if (currentItem == 0 && childCount == 0) {
                false
            } else {
                super.onInterceptTouchEvent(ev)
            }
        } else {
            false
        }
    }

    var imageList: MutableList<String> = ArrayList()
        set(value) {
            field = value
            if (value.size == 0) {
                return
            }
            mAdapter.data = value
            // 中间位置
            val middleIndex = Integer.MAX_VALUE / 2
            middleIndex % imageList.size
            setCurrentItem(middleIndex - middleIndex % imageList.size, false)
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
                currentItem += 1
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

    /**
     * Item点击事件
     */
    fun setOnItemClickedListener(listener: (Int) -> Unit) {
        mAdapter.onItemClickedListener = listener
    }
}