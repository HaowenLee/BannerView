package me.haowen.library.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView


internal class DefaultBannerAdapter(context: Context) : BannerAdapter<Any>(context) {

    override fun createImageView(context: Context, container: ViewGroup, position: Int): ImageView {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return imageView
    }
}