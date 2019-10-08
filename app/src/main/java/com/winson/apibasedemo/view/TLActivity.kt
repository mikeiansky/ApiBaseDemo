package com.winson.apibasedemo.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.winson.apibasedemo.R
import com.winson.apibasedemo.base.ApiItemListActivity
import com.winson.apibasedemo.base.BaseFragment
import kotlin.concurrent.fixedRateTimer

/**
 *
 * @author Winson
 */
class TLActivity : ApiItemListActivity() {

    class SelfFragment : BaseFragment() {

    }

    class SelfAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return SelfFragment()
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "TITLE $position"
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_tab_layout)

        val handler = Handler()
        val start_bg = findViewById<View>(R.id.start_bg)
        val start_desc = findViewById<View>(R.id.start_desc)
        val start_desc2 = findViewById<View>(R.id.start_desc_2)
        val start_logo = findViewById<View>(R.id.start_logo)
        val start_shan = findViewById<View>(R.id.start_shan)
        val start_person = findViewById<View>(R.id.start_person)
        val start_hb = findViewById<View>(R.id.start_hb)
        val tranX =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics)
        val tranY =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics)
        val tranY2 =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, resources.displayMetrics)

        val logoAnimator = ValueAnimator.ofFloat(0f, 1f)
        logoAnimator.duration = 1000
        logoAnimator.interpolator = BounceInterpolator()
        logoAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            start_desc.translationY = offset * tranY2
        }

        val rotateAnimator = ValueAnimator.ofFloat(0f, 45f)
        rotateAnimator.duration = 250
        rotateAnimator.interpolator = DecelerateInterpolator()
        rotateAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            start_hb.rotation = offset
        }

        val tranAnimator = ValueAnimator.ofFloat(0f, 1f)
        tranAnimator.duration = 750
        tranAnimator.interpolator = AnticipateInterpolator()
        tranAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            start_hb.translationX = offset * tranX
//            start_hb.translationY = offset * tranY
        }

        val alphaAnimator = ValueAnimator.ofFloat(0f, 1f)
        alphaAnimator.duration = 1500
        alphaAnimator.interpolator = DecelerateInterpolator()
        alphaAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            start_desc2.alpha = offset
            start_desc.alpha = 1f - offset
            start_bg.alpha = 1f - offset
//            start_logo_light.alpha = 1f - offset
            start_shan.alpha = 1f - offset
            start_person.alpha = 1f - offset
            start_hb.alpha = 1f - offset

        }

        val animatorSet = AnimatorSet()
//        animatorSet.playTogether(tranAnimator,alphaAnimator)
        animatorSet.playSequentially(rotateAnimator, tranAnimator, alphaAnimator, logoAnimator)

        handler.postDelayed({
            animatorSet.start()
        }, 500)

    }

}