package me.haowen.library.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import me.haowen.library.imageloader.ImageLoaderInterface
import me.haowen.library.R
import java.util.*
import kotlin.collections.ArrayList


class BannerPagerAdapter(private val mContext: Context) : PagerAdapter() {

    var imageLoader: ImageLoaderInterface? = null

    private val mViewCache: LinkedList<View> = LinkedList()

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val viewHolder: ViewHolder
        val contentView: View
        if (mViewCache.size == 0) {
            contentView = ImageView(mContext)
            viewHolder = ViewHolder()
            viewHolder.imageView = contentView
            contentView.setTag(R.string.holder_tag, viewHolder)
        } else {
            contentView = mViewCache.removeFirst()
            viewHolder = contentView.getTag(R.string.holder_tag) as ViewHolder
        }
        viewHolder.imageView.apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
            imageLoader?.displayImage(mContext, data[position % data.size], this)
        }
        container.addView(contentView)
        return contentView
    }

    override fun getCount(): Int {
        return if (data.isEmpty()) 0 else Integer.MAX_VALUE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val contentView = `object` as View
        container.removeView(contentView)
        mViewCache.add(contentView)
    }

    var data: MutableList<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder {
        lateinit var imageView: ImageView
    }
}