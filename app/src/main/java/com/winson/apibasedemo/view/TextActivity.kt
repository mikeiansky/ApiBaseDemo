package com.winson.apibasedemo.view

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.winson.apibasedemo.base.BaseActivity
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import android.widget.VideoView
import androidx.annotation.NonNull
import com.winson.apibasedemo.R
import com.winson.apibasedemo.utils.Constant


/**
 *
 * @date on 2019/10/8
 * @author Winson
 */
class TextActivity : BaseActivity() {

    inner class CenteredImageSpan(drawable: Drawable) : ImageSpan(drawable) {

        override fun draw(
            @NonNull canvas: Canvas, text: CharSequence,
            start: Int, end: Int, x: Float,
            top: Int, y: Int, bottom: Int, @NonNull paint: Paint
        ) {
            // image to draw
            val b = drawable
            // font metrics of text to be replaced
            val fm = paint.fontMetricsInt
            val transY = (y + fm.descent + y + fm.ascent) / 2 - b.bounds.bottom / 2

            canvas.save()
            canvas.translate(x, transY.toFloat())
            b.draw(canvas)
            canvas.restore()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_text)
        val customText = findViewById<TextView>(R.id.custom_text)
        val span = SpannableStringBuilder()
        span.append("0Hello World!")
        val fontSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12f, resources.displayMetrics)
        val red = TextDrawable.builder()
            .beginConfig()
            .textColor(Color.WHITE)
            .fontSize(fontSize.toInt())
            .endConfig()
            .buildRoundRect("AAA", Color.RED, 8)
        red.setBounds(0, 0, 120, 60)
        val bs = CenteredImageSpan(red)
//        val bs = ImageSpan(red)
        val ss = StyleSpan(Typeface.BOLD_ITALIC)
        span.setSpan(bs, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        customText.text = span

        val videoView = findViewById<VideoView>(R.id.video_view)
        videoView.setVideoPath(Constant.VIDEO_PATH)
        videoView.start()

        val videoFront = findViewById<ImageView>(R.id.video_front)

        val stroken =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources.displayMetrics)
        val bw =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250f, resources.displayMetrics)
        val bh =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, resources.displayMetrics)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.strokeWidth = stroken
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#ffffff")
        val source = Bitmap.createBitmap(bw.toInt(), bh.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(source)
        val corner =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources.displayMetrics)
        val offset = stroken / 2f
        val rectF = RectF(-offset, -offset, bw + offset, bh + offset)
        canvas.drawRoundRect(rectF, corner, corner, paint)

        videoFront.setImageBitmap(source)
    }

}