package com.winson.apibasedemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi

/**
 *@date on 2019/1/21
 *@Author Winson
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MyShadowView : View {

    constructor(context: Context?) : super(context) {

        init()
    }

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs) {

        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {

        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    var paint: Paint? = null
    var paintS: Paint? = null
    var rectF: RectF? = null
    var offset : Float = 0f

    fun init() {
        val radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics)
        offset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paintS = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = Color.WHITE
        paintS!!.color = Color.parseColor("#DEDEDE")
        val sw = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, context.resources.displayMetrics)
        paintS!!.strokeWidth = sw
        paintS!!.style = Paint.Style.STROKE
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        paint!!.setShadowLayer(radius, 0f, 0f, Color.parseColor("#DEDEDE"))

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectF = RectF(offset, offset, width.toFloat()-offset, height.toFloat()-offset)
//        canvas!!.drawCircle(width / 2f, width / 2f, width / 2f, paint)
        canvas!!.drawRoundRect(rectF, offset, offset, paint)
        canvas!!.drawRoundRect(rectF, offset, offset, paintS)
    }

}