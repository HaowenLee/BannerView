package me.haowen.library.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import me.haowen.library.R
import me.haowen.library.util.SizeUtil


class IndicatorLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {
    /**
     * 指示器数量
     */
    private var itemCount: Int = 0
    private var indicatorSize: Int = 0
    private var indicatorSpace: Int = 0
    /**
     * 指示点的View
     */
    private var indicationPointView: ImageView? = null

    /**
     * 未选中的指示器图
     */
    var unselectedDrawable: Drawable? = null

    /**
     * 选中时的指示器图
     */
    var selectedDrawable: Drawable? = null

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.IndicatorLayout, 0, 0)
        if (!isInEditMode) {
            itemCount = typedArray.getInteger(R.styleable.IndicatorLayout_itemCount, 0)
            unselectedDrawable = typedArray.getDrawable(R.styleable.IndicatorLayout_unselectedDrawable)
            indicatorSize =
                typedArray.getDimension(R.styleable.IndicatorLayout_indicatorSize, SizeUtil.dp2px(5f).toFloat())
                    .toInt()
            selectedDrawable = typedArray.getDrawable(R.styleable.IndicatorLayout_selectedDrawable)
            indicatorSpace =
                typedArray.getDimension(R.styleable.IndicatorLayout_indicatorSpace, SizeUtil.dp2px(10f).toFloat())
                    .toInt()
        }
        typedArray.recycle()

        // 保证精度
        indicatorSize = indicatorSize / 2 * 2
        indicatorSpace = indicatorSpace / 2 * 2
    }

    /**
     * 关联ViewPager
     *
     * @param viewPager ViewPager
     */
    fun setUpWithViewPager(viewPager: ViewPager, itemCount: Int) {
        this.itemCount = itemCount
        initIndicatorView()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // indicationPointView!!.translationX = indicatorSpace * (position % itemCount + positionOffset)
            }

            override fun onPageSelected(position: Int) {
                indicationPointView!!.translationX = indicatorSpace * (position % itemCount).toFloat()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    fun initIndicatorView() {
        removeAllViews()

        if (itemCount <= 0) {
            return
        }

        addNormalIndicationView(context)

        addIndicationPointView(context)
    }

    /**
     * 创建指示点的View
     *
     * @param context 上下文
     */
    private fun addIndicationPointView(context: Context) {
        indicationPointView = ImageView(context)
        indicationPointView!!.scaleType = ImageView.ScaleType.FIT_XY
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )

        params.gravity = Gravity.CENTER_VERTICAL

        // 为小圆点左右添加间距
        params.leftMargin = (indicatorSpace - indicatorSize) / 2
        params.rightMargin = (indicatorSpace - indicatorSize) / 2
        // 手动给小圆点一个大小
        params.height = indicatorSize
        params.width = indicatorSize

        indicationPointView!!.setImageDrawable(selectedDrawable)

        addView(indicationPointView, params)
    }

    private fun addNormalIndicationView(context: Context) {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(linearLayout, lp)
        linearLayout.gravity = Gravity.CENTER_VERTICAL

        for (i in 0 until itemCount) {
            val normalView = ImageView(context)
            normalView.scaleType = ImageView.ScaleType.FIT_XY
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )

            // 为小圆点左右添加间距
            params.leftMargin = (indicatorSpace - indicatorSize) / 2
            params.rightMargin = (indicatorSpace - indicatorSize) / 2
            // 手动给小圆点一个大小
            params.height = indicatorSize
            params.width = indicatorSize

            normalView.setImageDrawable(unselectedDrawable)
            // 为LinearLayout添加ImageView
            linearLayout.addView(normalView, params)
        }
    }
}
