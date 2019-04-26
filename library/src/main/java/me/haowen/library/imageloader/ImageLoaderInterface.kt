package me.haowen.library.imageloader

import android.content.Context
import android.view.View


interface ImageLoaderInterface<T : View> {

    fun displayImage(context: Context, path: Any, imageView: T, position: Int)

    fun createImageView(context: Context): T
}