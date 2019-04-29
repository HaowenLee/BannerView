package me.haowen.library

import me.haowen.library.imageloader.ImageLoader
import me.haowen.library.view.BannerViewPager

/**
 * 轮播图控制
 */
interface IBannerControl {

    /**
     * 设置Banner是否可以滑动
     *
     * @param
     */
    fun setScrollable(scrollable: Boolean)

    /**
     * 设置Banner切换的动画时长
     *
     * @param duration Banner切换的动画时长
     */
    fun setDuration(duration: Int)

    /**
     * 设置Banner页面停留时间
     *
     * @param delayTime Banner页面停留时间
     */
    fun setDelayTime(delayTime: Long)

    /**
     * 设置Banner是否自动轮播
     *
     * @param autoPlay 是否自动轮播
     */
    fun setAutoPlay(autoPlay: Boolean)

    /**
     * 设置Banner图片加载器（即自定义图片加载方式）
     *
     * @param imageLoader 图片加载器
     */
    fun setImageLoader(imageLoader: ImageLoader)

    /**
     * 设置Banner图片数据源
     *
     * @param imageList 图片数据源
     */
    fun setImageList(imageList: MutableList<Any>)

    /**
     * 获取Banner图片的数据源
     *
     * @return 图片数据源
     */
    fun getImageList(): MutableList<Any>

    /**
     * 获取Banner的ViewPager
     *
     * @return BannerViewPager
     */
    fun getViewPager(): BannerViewPager

    /**
     * 开始自动轮播
     */
    fun startAutoPlay()

    /**
     * 停止自动轮播
     */
    fun stopAutoPlay()

    /**
     * Item点击事件
     */
    fun setOnItemClickedListener(listener: (Int) -> Unit)
}