package com.winson.apibasedemo.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.TypedValue

/**
 *@date on 2019/1/22
 *@Author Winson
 */
class ShadowDrawable(val context: Context) : Drawable() {

    private var paint: Paint? = null
    private var rectF: RectF? = null
    private var paintS: Paint? = null
    private var offset: Float = 0f

    init {
        val radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics)
        offset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = Color.RED

        paintS = Paint(Paint.ANTI_ALIAS_FLAG)
        paintS!!.color = Color.parseColor("#DEDEDE")
        paintS!!.style = Paint.Style.STROKE

        rectF = RectF()
        paint!!.setShadowLayer(radius, 0f, 0f, Color.parseColor("#DEDEDE"))


    }

    override fun draw(canvas: Canvas) {
        rectF!!.set(offset, offset, bounds.width() - offset, bounds.height() - offset)
        canvas.drawRoundRect(rectF!!, offset, offset, paint!!)
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }

}