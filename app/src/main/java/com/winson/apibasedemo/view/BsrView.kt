package com.winson.apibasedemo.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
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
class BsrView : View {

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
    private val path = Path()

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = Color.parseColor("#46CEC4")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        path.reset()
        path.lineTo(0f, height * 3f / 4f)
        path.cubicTo(0f, height * 3f / 4f, width / 2f, height * 5f / 4f, width.toFloat(), height * 3f / 4f)
        path.lineTo(width.toFloat(), 0f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(path, paint!!)

    }

}