package me.haowen.library.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import me.haowen.library.R
import me.haowen.library.imageloader.ImageLoader
import java.util.*
import kotlin.collections.ArrayList


abstract class BannerAdapter<T : Any>(private val mContext: Context) : PagerAdapter() {

    /**
     * 图片加载
     */
    var imageLoader: ImageLoader? = null
    /**
     * View缓存
     */
    private val mViewCache: LinkedList<View> = LinkedList()

    /**
     * 数据
     */
    var data: MutableList<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return if (data.isEmpty()) 0 else Integer.MAX_VALUE
    }

    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view == any
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val realIndex = getRealPosition(position)
        val viewHolder: ViewHolder
        val contentView: View
        if (mViewCache.isEmpty()) {
            contentView = createImageView(mContext, container, realIndex)
            viewHolder = ViewHolder(contentView)
            contentView.setTag(R.string.holder_tag, viewHolder)
        } else {
            contentView = mViewCache.removeFirst()
            viewHolder = contentView.getTag(R.string.holder_tag) as ViewHolder
        }
        viewHolder.imageView.apply {
            imageLoader?.displayImage(mContext, data[realIndex], this, realIndex)
            setOnClickListener {
                onItemClickedListener?.invoke(realIndex)
            }
        }
        container.addView(contentView)
        return contentView
    }

    abstract fun createImageView(context: Context, container: ViewGroup, position: Int): ImageView

    /**
     * 获取真实的位置
     *
     * @param position position
     * @return 真实的位置
     */
    fun getRealPosition(position: Int): Int {
        if (data.size == 0) {
            return 0
        }
        return position % data.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        val contentView = any as View
        container.removeView(contentView)
        mViewCache.add(contentView)
    }

    class ViewHolder(var imageView: ImageView)

    /**
     * 点击事件
     */
    var onItemClickedListener: ((Int) -> Unit)? = null
}