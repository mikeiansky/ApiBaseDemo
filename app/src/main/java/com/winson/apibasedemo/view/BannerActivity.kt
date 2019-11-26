package com.winson.apibasedemo.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.BaseActivity

/**
 * @date 2019/11/25
 * @author winson-zhou
 */
class BannerActivity : BaseActivity() {

    class TestPageAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = TextView(container.context)
            val lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.gravity = Gravity.CENTER
            imageView.text = "$position"
            imageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
            imageView.layoutParams = lp
            imageView.setBackgroundColor((Math.random() * Color.RED).toInt())
            container.addView(imageView)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return 10
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_banner)

        val stairBannerView = findViewById<StairBannerView>(R.id.stair_banner_view)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val adapter = TestPageAdapter()
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            private var lastPosition = 1

            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (lastPosition != viewPager.currentItem) {
                        stairBannerView.notifyUpdate()
                        lastPosition = viewPager.currentItem
                    }
                    Log.d("TAG", "WinsonPage onPageScrollStateChanged -->")
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d(
                    "TAG",
                    "WinsonPage onPageScrolled --> position: $position , lastPosition:${lastPosition} , positionOffset:$positionOffset"
                )
                if (position <= lastPosition) {
                    stairBannerView.actionScroll(position < lastPosition, positionOffset)
                }
            }

            override fun onPageSelected(position: Int) {
//                stairBannerView.notifyUpdate()
                Log.d("TAG", "WinsonPage onPageSelected --> $position")
//                stairBannerView.notifyUpdate()

            }

        })


    }

}