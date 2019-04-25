package me.haowen.library.imageloader

import android.content.Context
import android.widget.ImageView

/**
 * ================================================
 * 作    者：Herve、Li
 * 创建日期：2019/4/25
 * 描    述：
 * 修订历史：
 * ================================================
 */
interface ImageLoaderInterface {

    fun displayImage(context: Context, path: Any, imageView: ImageView)
}