package com.winson.apibasedemo.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.winson.apibasedemo.R

/**
 * @date 2019/11/25
 * @author winson-zhou
 */
class StairBannerView : FrameLayout {

    interface Item {
        fun getCover(): String
    }

    @SuppressLint("NewApi")
    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    @SuppressLint("NewApi")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    @SuppressLint("NewApi")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr, 0)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private val fiveScale = 0.7f
    private var fiveTranslateX = 0f
    private var b45w = 0f

    private val fourScale = 0.8f
    private var fourTranslateX = 0f
    private var b34w = 0f

    private val threeScale = 0.9f
    private var threeTranslateX = 0f

    private val aspectRatio = 2.38f
    private var paddTB = 0
    private var paddLR = 0
    private var offset = 0
    private var radius = 0f
    private var datas: ArrayList<Item>? = null
    private var firstShowPosition = 0

    private fun fromLayoutParams(): LayoutParams {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        val leftMargin = paddLR
        val rightMargin = paddLR + offset * 2
        layoutParams.leftMargin = leftMargin
        layoutParams.rightMargin = rightMargin
        layoutParams.topMargin = paddTB
        layoutParams.bottomMargin = paddTB
        return layoutParams
    }

    private fun createCoverView(): SimpleDraweeView {
        val rp = RoundingParams.fromCornersRadius(radius)
        val coverView = SimpleDraweeView(context)
        coverView.hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP
        coverView.hierarchy.roundingParams = rp
        coverView.aspectRatio = aspectRatio
        return coverView
    }

    fun notifyUpdate(toNext: Boolean, firstShowPosition: Int) {
        if (toNext) {
            // top
            val topView = getChildAt(childCount - 1)
            removeView(topView)

            // add bottom
            addView(topView, 0)
        } else {
            // bottom
            val bottomView = getChildAt(0)
            removeView(bottomView)

            // add bottom
            addView(bottomView)
        }
        refreshShowPosition(firstShowPosition)
        refreshLayoutParams()
    }

    fun actionScroll(prev: Boolean, scrollOffset: Float) {
        if (prev) {
            // first hierarchy view
            val firstView = getChildAt(4)
            firstView.translationX = -(firstView.width + offset * 5) * scrollOffset

            // second hierarchy view
            val secondView = getChildAt(3)
            val ss = threeScale + (1f - threeScale) * scrollOffset
            secondView.scaleX = ss
            secondView.scaleY = ss
            secondView.translationX = threeTranslateX * (1 - scrollOffset)

            // third hierarchy view
            val ts = fourScale + (threeScale - fourScale) * scrollOffset
            val thirdView = getChildAt(2)
            thirdView.translationX = threeTranslateX + b34w * (1 - scrollOffset)
            thirdView.scaleX = ts
            thirdView.scaleY = ts

            // four hierarchy view
            val fs = fiveScale + (fourScale - fiveScale) * scrollOffset
            val fourView = getChildAt(1)
            fourView.translationX = fourTranslateX + b45w * (1 - scrollOffset)
            fourView.scaleX = fs
            fourView.scaleY = fs
            fourView.alpha = scrollOffset

        } else {
            // second hierarchy view
            val secondView = getChildAt(3)
            secondView.translationX = -(secondView.width + paddTB + paddLR) * scrollOffset

            // third hierarchy view
            val ts = threeScale + (1f - threeScale) * scrollOffset
            val thirdView = getChildAt(2)
            thirdView.translationX = threeTranslateX * (1 - scrollOffset)
            thirdView.scaleX = ts
            thirdView.scaleY = ts

            // four hierarchy view
            val fs = fourScale + (threeScale - fourScale) * scrollOffset
            val fourView = getChildAt(1)
            fourView.translationX = threeTranslateX + b34w * (1 - scrollOffset)
            fourView.scaleX = fs
            fourView.scaleY = fs

            // five hierarchy view
            val fiveS = fiveScale + (fourScale - fiveScale) * scrollOffset
            val fiveView = getChildAt(0)
            fiveView.translationX = fourTranslateX + b45w * (1 - scrollOffset)
            fiveView.alpha = scrollOffset
            fiveView.scaleX = fiveS
            fiveView.scaleY = fiveS
        }
    }

    private fun refreshLayoutParams() {
        for (i in 0 until childCount) {
            getChildAt(i).layoutParams = fromLayoutParams()
        }

        val topImage = getChildAt(childCount - 1)
        fiveTranslateX = topImage.width * (1 - fiveScale) / 2f + offset + offset + offset
        fourTranslateX = topImage.width * (1 - fourScale) / 2f + offset + offset
        threeTranslateX = topImage.width * (1 - threeScale) / 2f + offset
        b45w = fiveTranslateX - fourTranslateX
        b34w = fourTranslateX - threeTranslateX

        // five
        val fiveImage = getChildAt(0)
        fiveImage.scaleX = fiveScale
        fiveImage.scaleY = fiveScale
        fiveImage.alpha = 0.0f
        fiveImage.translationX = fiveTranslateX

        // four
        val fourImage = getChildAt(1)
        fourImage.scaleX = fourScale
        fourImage.scaleY = fourScale
        fourImage.alpha = 1f
        fourImage.translationX = fourTranslateX

        // three
        val threeImage = getChildAt(2)
        threeImage.scaleX = threeScale
        threeImage.scaleY = threeScale
        threeImage.alpha = 1f
        threeImage.translationX = threeTranslateX

        val twoImage = getChildAt(3)
        twoImage.scaleX = 1f
        twoImage.scaleY = 1f
        twoImage.alpha = 1f
        twoImage.translationX = 0f

        // one
        val oneTx = topImage.width * -1f - offset * 5
        topImage.scaleX = 1f
        topImage.scaleY = 1f
        topImage.alpha = 1f
        topImage.translationX = oneTx
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        clipChildren = false
        clipToPadding = false
        paddTB = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            50f,
            context.resources.displayMetrics
        ).toInt()

        paddLR = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            20f,
            context.resources.displayMetrics
        ).toInt()

        offset = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            10f,
            context.resources.displayMetrics
        ).toInt()

        radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            5f,
            context.resources.displayMetrics
        )


        for ((index) in (0..4).withIndex()) {
            val coverView = createCoverView()
            addView(coverView)
        }

        refreshLayoutParams()
        viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                refreshLayoutParams()
                viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }

        })

    }

    fun refreshData(datas: ArrayList<Item>?) {
        this.datas = datas
        refreshShowPosition(firstShowPosition)
    }

    private fun getPosition(position: Int): Int {
        val size = datas!!.size
        return (size + position) % size
    }

    private fun refreshShowPosition(firstShowPosition: Int) {
        this.firstShowPosition = firstShowPosition
        if (datas == null || datas!!.isEmpty()) {
            for ((index) in (0 until childCount).withIndex()) {
                (getChildAt(index) as SimpleDraweeView).setImageURI("")
            }
        } else {
            (getChildAt(4) as SimpleDraweeView).setImageURI(datas!![getPosition(firstShowPosition - 1)].getCover())
            (getChildAt(3) as SimpleDraweeView).setImageURI(datas!![getPosition(firstShowPosition)].getCover())
            (getChildAt(2) as SimpleDraweeView).setImageURI(datas!![getPosition(firstShowPosition + 1)].getCover())
            (getChildAt(1) as SimpleDraweeView).setImageURI(datas!![getPosition(firstShowPosition + 2)].getCover())
            (getChildAt(0) as SimpleDraweeView).setImageURI(datas!![getPosition(firstShowPosition + 3)].getCover())
        }
    }

}