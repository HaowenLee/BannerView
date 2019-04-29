package me.haowen.library.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
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
    /**
     * 指示点的View
     */
    private lateinit var selectedItem: View
    /**
     * 指示器的大小
     */
    var indicatorSize: Int = 0
    /**
     * 指示器的间距
     */
    var indicatorSpace: Int = 0
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
    fun setUpWithViewPager(viewPager: BaseViewPager, itemCount: Int) {
        this.itemCount = itemCount
        initIndicatorView()
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val realIndex = viewPager.getRealPosition(position)
                if (realIndex == itemCount - 1) {
                    return
                }
                selectedItem.translationX = indicatorSpace * (realIndex + positionOffset)
            }

            override fun onPageSelected(position: Int) {
                val realIndex = viewPager.getRealPosition(position)
                if (realIndex == 0 || realIndex == itemCount - 1) {
                    selectedItem.translationX = (indicatorSpace * realIndex).toFloat()
                }
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

        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        addView(linearLayout, lp)
        linearLayout.gravity = Gravity.CENTER_VERTICAL

        for (i in 0 until itemCount) {
            linearLayout.addView(getDefaultItem())
        }
        selectedItem = getSelectedItem()
        addView(selectedItem)
    }

    private fun getDefaultItem(): View {
        val imageView = ImageView(context)
        val lp = MarginLayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )

        lp.apply {
            leftMargin = (indicatorSpace - indicatorSize) / 2
            rightMargin = (indicatorSpace - indicatorSize) / 2
            height = indicatorSize
            width = indicatorSize
        }

        imageView.setImageDrawable(unselectedDrawable)
        imageView.layoutParams = lp
        return imageView
    }

    private fun getSelectedItem(): View {
        val imageView = ImageView(context)
        val lp = MarginLayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
        )

        lp.apply {
            leftMargin = (indicatorSpace - indicatorSize) / 2
            rightMargin = (indicatorSpace - indicatorSize) / 2
            height = indicatorSize
            width = indicatorSize
        }

        imageView.setImageDrawable(selectedDrawable)
        imageView.layoutParams = lp
        return imageView
    }
}
