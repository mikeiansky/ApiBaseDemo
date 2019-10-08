package com.winson.apibasedemo.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import com.winson.apibasedemo.R

/**
 *@date on 2019/1/21
 *@Author Winson
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CardShadowView : View {

    constructor(context: Context?) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private var paint: Paint? = null
    private var paintS: Paint? = null
    private var rectF: RectF = RectF()
    private var offset: Float = 0f
    private var radius: Float = 0f

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.CardShadowView)

        val defaultRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.displayMetrics)
        radius = a.getDimension(R.styleable.CardShadowView_corner, defaultRadius)
        val defaultOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, resources.displayMetrics)
        offset = a.getDimension(R.styleable.CardShadowView_offset, defaultOffset)

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paintS = Paint(Paint.ANTI_ALIAS_FLAG)

        val frameColor = a.getColor(R.styleable.CardShadowView_frame_color, Color.parseColor("#ffffffff"))
        val shadowColor = a.getColor(R.styleable.CardShadowView_shadow_color, Color.parseColor("#20000000"))

        paint!!.color = frameColor
//        paintS!!.color = Color.parseColor("#DEDEDE")

//        val sw = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, context.resources.displayMetrics)
//        paintS!!.strokeWidth = sw
//        paintS!!.style = Paint.Style.STROKE
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint!!.setShadowLayer(offset, 0f, 0f, shadowColor)

        a.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        rectF.set(offset, offset, width.toFloat() - offset, height.toFloat() - offset)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawRoundRect(rectF, radius, radius, paint)
//        canvas.drawRoundRect(rectF, offset, offset, paintS)
    }

}