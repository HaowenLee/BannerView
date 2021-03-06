package me.haowen.bannerview

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_home.*
import me.haowen.library.imageloader.ImageLoader
import me.haowen.library.util.SizeUtil

class HomeActivity : AppCompatActivity() {

    private val mainColors = arrayListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    private var firstColor = true
    private var pageState: Int = ViewPager.SCROLL_STATE_IDLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bannerViewPager.duration = 300

        bannerViewPager.imageLoader = object : ImageLoader() {

            override fun displayImage(context: Context, path: Any, imageView: ImageView, position: Int) {
                Glide.with(context)
                    .asBitmap()
                    .load(path)
                    .into(imageView)
            }
        }
        bannerViewPager.imageList = arrayListOf(
            "https://imagev2.xmcdn.com/group58/M02/BD/56/wKgLc1y1gE6jeL2aAAIZXzsRjZ4383.jpg",
            "https://imagev2.xmcdn.com/group59/M09/03/98/wKgLeFysXxnShqkvAAIXR3-sGDI402.jpg",
            "https://imagev2.xmcdn.com/group56/M08/70/24/wKgLgFyTBbnTuBaZAAIlw2Q811M585.jpg",
            "https://imagev2.xmcdn.com/group58/M05/55/56/wKgLglywKLnzf2hcAAq_7POb1CQ160.png",
            "https://imagev2.xmcdn.com/group57/M06/41/22/wKgLd1ydj0CC3oKrAAGHh4YsIF4869.jpg",
            "https://imagev2.xmcdn.com/group59/M07/BD/7E/wKgLeFy1gM6Qjyv2AAG9rs-lTV0644.jpg",
            "https://imagev2.xmcdn.com/group53/M01/98/92/wKgLcVwImsHBD5vQAAe-xf_dCis681.png",
            "https://imagev2.xmcdn.com/group58/M0B/9D/F0/wKgLglyz7aSgk1M0AAF4LB3B8J4801.jpg",
            "https://imagev2.xmcdn.com/group59/M06/55/62/wKgLeFywKQ3BkdxvAA-pd4FDzJU540.png",
            "https://imagev2.xmcdn.com/group60/M0B/B0/D4/wKgLeVzBUuvjuAUaAACvtZVhMmQ766.png"
        )

        // Item点击事件
        bannerViewPager.setOnItemClickedListener { position ->
            Toast.makeText(this@HomeActivity, position.toString(), Toast.LENGTH_SHORT).show()
        }

        indicatorLayout.setUpWithViewPager(bannerViewPager, mainColors.size)

        initBannerView()
    }

    /**
     * 组合后的BannerView（可自定义性低）
     */
    private fun initBannerView() {
        bannerView.setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any, imageView: ImageView, position: Int) {
                Glide.with(context)
                    .asBitmap()
                    .load(path)
                    .transform(CenterCrop(), RoundedCorners(SizeUtil.dp2px(8f)))
                    .listener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // 没颜色
                            if (mainColors[position] == 0) {
                                val mainColor = generateColor(resource)
                                mainColors[position] = mainColor
                                // IDLE状态的，并且页码相同,第一次
                                if (firstColor && pageState == ViewPager.SCROLL_STATE_IDLE && position == bannerView.getViewPager().getRealPosition(
                                        position
                                    )
                                ) {
                                    ivBannerBg.setBackgroundColor(mainColor)
                                    firstColor = false
                                }
                            }
                            return false
                        }
                    })
                    .into(imageView)
            }
        })

        bannerView.setImageList(
            arrayListOf(
                "https://imagev2.xmcdn.com/group58/M02/BD/56/wKgLc1y1gE6jeL2aAAIZXzsRjZ4383.jpg",
                "https://imagev2.xmcdn.com/group59/M09/03/98/wKgLeFysXxnShqkvAAIXR3-sGDI402.jpg",
                "https://imagev2.xmcdn.com/group56/M08/70/24/wKgLgFyTBbnTuBaZAAIlw2Q811M585.jpg",
                "https://imagev2.xmcdn.com/group58/M05/55/56/wKgLglywKLnzf2hcAAq_7POb1CQ160.png",
                "https://imagev2.xmcdn.com/group57/M06/41/22/wKgLd1ydj0CC3oKrAAGHh4YsIF4869.jpg",
                "https://imagev2.xmcdn.com/group59/M07/BD/7E/wKgLeFy1gM6Qjyv2AAG9rs-lTV0644.jpg",
                "https://imagev2.xmcdn.com/group53/M01/98/92/wKgLcVwImsHBD5vQAAe-xf_dCis681.png",
                "https://imagev2.xmcdn.com/group58/M0B/9D/F0/wKgLglyz7aSgk1M0AAF4LB3B8J4801.jpg",
                "https://imagev2.xmcdn.com/group59/M06/55/62/wKgLeFywKQ3BkdxvAA-pd4FDzJU540.png",
                "https://imagev2.xmcdn.com/group60/M0B/B0/D4/wKgLeVzBUuvjuAUaAACvtZVhMmQ766.png"
            )
        )

        // 设置指示器
        bannerView.indicatorLayout.apply {
            selectedDrawable = resources.getDrawable(R.drawable.bg_viewpager_indicator_point_selected)
            unselectedDrawable = resources.getDrawable(R.drawable.bg_viewpager_indicator_point_unselected)
            initIndicatorView()
            setBackgroundResource(R.drawable.bg_indicator_layout)
            setPadding(SizeUtil.dp2px(3f), SizeUtil.dp2px(3f), SizeUtil.dp2px(3f), SizeUtil.dp2px(3f))
        }

        bannerView.setOnItemClickedListener { position ->
            Toast.makeText(this@HomeActivity, position.toString(), Toast.LENGTH_SHORT).show()
        }

        // 以下是主题色获取（可以不关注）
        val argbEvaluator = ArgbEvaluator()

        bannerView.getViewPager().addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
                pageState = state
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // 获取下一页位置索引
                val realIndex = bannerView.getViewPager().getRealPosition(position)
                val nextPosition = (realIndex + 1 + bannerView.getImageList().size) % bannerView.getImageList().size
                val evaluate = argbEvaluator.evaluate(
                    positionOffset, mainColors[realIndex],
                    mainColors[nextPosition]
                ) as Int
                ivBannerBg.setBackgroundColor(evaluate)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    /**
     * 获取主题色
     *
     * @param bitmap 图片位图
     * @return 主题色
     */
    private fun generateColor(bitmap: Bitmap?): Int {
        // 默认颜色
        val defaultColor = -0x323233
        var mainColor = 0

        if (bitmap == null || bitmap.isRecycled) {
            return defaultColor
        }

        try {
            val palette =
                Palette.from(bitmap).setRegion(0, 0, (bitmap.width * 0.02).toInt(), (bitmap.width * 0.02).toInt())
                    .generate()
            mainColor = palette.getDominantColor(-1)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (mainColor != -1 && mainColor != 0) {
            return mainColor
        }

        try {
            mainColor = bitmap.getPixel(bitmap.width / 2, bitmap.height / 2)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (mainColor != -1 && mainColor != 0) {
            return mainColor
        }

        try {
            mainColor = bitmap.getPixel(bitmap.width - 1, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (mainColor != -1 && mainColor != 0) {
            mainColor
        } else defaultColor
    }

    override fun onResume() {
        super.onResume()
        bannerViewPager.startAutoPlay()
        bannerView.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        bannerViewPager.stopAutoPlay()
        bannerView.stopAutoPlay()
    }
}
