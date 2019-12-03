package com.winson.apibasedemo.view

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.annotation.RequiresApi
import com.winson.apibasedemo.R

/**
 *@date on 2019/3/12
 *@Author Winson
 */
class BesselView : View {

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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private var offset = 4f
    private var paint: Paint? = null
    private var path = Path()

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
//        val a = context!!.obtainStyledAttributes(attrs, R.styleable.ProgressView)
        offset = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            2f,
            context!!.resources.displayMetrics
        )
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = Color.WHITE
//        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        path.reset()
        path.moveTo(0f - offset, 0f - offset)
        path.lineTo(0f - offset, height.toFloat() + offset)
        path.lineTo(width.toFloat() + offset, height.toFloat() + offset)
        path.lineTo(width.toFloat() + offset, 0f - offset)
        path.quadTo(width / 2f, 2f * height.toFloat() + 2 * offset, 0f - offset, 0f - offset)
        path.lineTo(0f - offset, 0f - offset)
        path.close()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(path, paint)
    }

}