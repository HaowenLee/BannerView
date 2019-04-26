package me.haowen.library.imageloader

import android.content.Context
import android.widget.ImageView


abstract class ImageLoader : ImageLoaderInterface<ImageView> {

    override fun createImageView(context: Context): ImageView {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return imageView
    }
}