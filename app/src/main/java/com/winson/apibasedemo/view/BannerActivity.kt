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
import kotlin.math.abs

/**
 * @date 2019/11/25
 * @author winson-zhou
 */
class BannerActivity : BaseActivity() {

    private val imageUrl1 =
        "https://img.shixijob.net/college/20190921/E08197083058746ACB97795FAE3FEC9F.jpg"
    private val imageUrl2 =
        "https://img.shixijob.net/college/20190921/370B196584E027705A6B5BD2DC6417E8.jpeg"
    private val imageUrl3 =
        "https://img.shixijob.net/college/20190829/6013B011049502A0C36E7BFFB4AB8D7F.png"
    private val imageUrl4 =
        "https://img.shixijob.net/college/20190829/B0B3F990EC4292751E7A7DDA1CE3263E.png"
    private val imageUrl5 =
        "https://img.shixijob.net/college/20190829/A4DE10855E024376B4AC0AE111E0A1F7.png"
    private val imageUrl6 =
        "https://img.shixijob.net/college/20190830/54B10C10D4DE1B4FE2315C184B5D9395.jpg"
    private val testImageUrl =
        arrayListOf(imageUrl1, imageUrl2, imageUrl3, imageUrl4, imageUrl5, imageUrl6)

    private val coverDatas = arrayListOf(
        object : StairBannerView.Item {
            override fun getCover(): String {
                return imageUrl1
            }
        },
        object : StairBannerView.Item {
            override fun getCover(): String {
                return imageUrl2
            }
        },
        object : StairBannerView.Item {
            override fun getCover(): String {
                return imageUrl3
            }
        },
        object : StairBannerView.Item {
            override fun getCover(): String {
                return imageUrl4
            }
        },
        object : StairBannerView.Item {
            override fun getCover(): String {
                return imageUrl5
            }
        }
    )

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
            return 5
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
                if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_DRAGGING) {
                    val current = viewPager.currentItem
                    Log.d(
                        "TAG",
                        "WinsonPage onPageScrollStateChanged -->state:$state , lastPosition:$lastPosition, current:$current"
                    )
                    if (lastPosition != current) {
                        val offset = current - lastPosition
                        if (abs(offset) >= 2) {
                            if (offset > 0) {
                                stairBannerView.notifyUpdate(false, current - 1)
                            } else {
                                stairBannerView.notifyUpdate(true, current - 1)
                            }
                        } else {
                            stairBannerView.notifyUpdate(current > lastPosition, current - 1)
                        }
                        lastPosition = viewPager.currentItem
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
//                Log.d(
//                    "TAG",
//                    "WinsonPage onPageScrolled --> position: $position , lastPosition:${lastPosition} , positionOffset:$positionOffset"
//                )
                if (position <= lastPosition) {
                    stairBannerView.actionScroll(position < lastPosition, positionOffset)
                }
            }

            override fun onPageSelected(position: Int) {
//                stairBannerView.notifyUpdate()
//                Log.d("TAG", "WinsonPage onPageSelected --> $position")
//                stairBannerView.notifyUpdate()

            }

        })

        stairBannerView.refreshData(coverDatas)
        findViewById<View>(R.id.refresh).setOnClickListener {
            stairBannerView.refreshData(coverDatas)
        }

    }

}