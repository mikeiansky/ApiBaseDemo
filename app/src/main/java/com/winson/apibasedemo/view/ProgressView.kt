package com.winson.apibasedemo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.winson.apibasedemo.R

/**
 *@date on 2019/3/12
 *@Author Winson
 */
class ProgressView : View {

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

    private var paint: Paint? = null
    private var indicatorPaint: Paint? = null
    private var strokenWidth = 0f
    private var indicatorRectF: RectF? = null
    private var progress: Int = 0

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val a = context!!.obtainStyledAttributes(attrs, R.styleable.ProgressView)

        strokenWidth = a.getDimension(R.styleable.ProgressView_stroke_with, 0f)
        indicatorRectF = RectF()

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.color = a.getColor(R.styleable.ProgressView_bg_color, Color.parseColor("#ffffff"))
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = strokenWidth

        indicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        indicatorPaint!!.color = a.getColor(R.styleable.ProgressView_progress_color, Color.parseColor("#ffffff"))
        indicatorPaint!!.style = Paint.Style.STROKE
        indicatorPaint!!.strokeWidth = strokenWidth

        a.recycle()
    }

    fun setProgress(progress: Int) {
        this.progress = progress
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawCircle(width / 2f, height / 2f, width / 2f - strokenWidth / 2f, paint)
        indicatorRectF!!.set(
            strokenWidth / 2f, strokenWidth / 2f,
            width.toFloat() - strokenWidth / 2f, height.toFloat() - strokenWidth / 2f
        )
        canvas!!.drawArc(indicatorRectF, -90f, 360f * progress / 100f, false, indicatorPaint)
    }

}