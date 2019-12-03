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
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_text)

    }

}