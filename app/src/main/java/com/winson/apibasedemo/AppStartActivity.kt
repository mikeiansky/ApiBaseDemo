package com.winson.apibasedemo

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import com.winson.apibasedemo.base.BaseActivity

/**
 *
 * @date on 2019/8/31
 * @author Winson
 */
class AppStartActivity : BaseActivity() {


    private var animatorSet: AnimatorSet? = null
    private var finish = false

    override fun onDestroy() {
        super.onDestroy()
        finish = true
        animatorSet?.cancel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_app_start)

        val handler = Handler()
        val start_bg = findViewById<View>(R.id.start_bg)
        val start_desc = findViewById<View>(R.id.start_desc)
        val start_desc2 = findViewById<View>(R.id.start_desc_2)
        val start_logo = findViewById<View>(R.id.start_logo)
        val start_shan = findViewById<View>(R.id.start_shan)
        val start_person = findViewById<View>(R.id.start_person)
        val start_hb = findViewById<View>(R.id.start_hb)
        val wind = findViewById<View>(R.id.wind)
        val tranXOffset =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, resources.displayMetrics)
        val tranX =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics)
        val tranY =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics)
        val tranY2 =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 66f, resources.displayMetrics)
        val jumpOffset =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, resources.displayMetrics)

        val bounce = resources.displayMetrics.heightPixels / 2f + tranY2
        val logoAnimator = ValueAnimator.ofFloat(0f, 1f)
        logoAnimator.duration = 750
        logoAnimator.interpolator = BounceInterpolator()
        logoAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            start_desc2.translationY = offset * bounce
        }

        val rotateAnimator = ValueAnimator.ofFloat(0f, 45f)
        rotateAnimator.duration = 200
        rotateAnimator.interpolator = DecelerateInterpolator()
        rotateAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            start_hb.rotation = offset
        }

        val jumpAnimator = ValueAnimator.ofFloat(0f, 1f)
        jumpAnimator.duration = 250
        jumpAnimator.interpolator = DecelerateInterpolator()
        jumpAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            start_person.translationY = jumpOffset * offset
            start_person.translationX = jumpOffset * offset
        }

        val tranAnimator = ValueAnimator.ofFloat(0f, 1f)
        tranAnimator.duration = 700
        tranAnimator.interpolator = AnticipateInterpolator()
        tranAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
            wind.alpha = offset * 2
            start_hb.translationX = offset * tranX
            start_person.translationX = offset * tranX + jumpOffset
            start_person.rotation = -10f * offset
        }

        val alphaAnimator = ValueAnimator.ofFloat(0f, 1f)
        alphaAnimator.duration = 750
        alphaAnimator.interpolator = DecelerateInterpolator()
        alphaAnimator.addUpdateListener {
            val offset = it.animatedValue as Float
//            start_desc2.alpha = offset
            start_desc.alpha = 1f - offset
//            start_bg_light.alpha = 1f - offset
            start_logo.alpha = 1f - offset
            start_shan.alpha = 1f - offset
            start_person.alpha = 1f - offset
            start_hb.alpha = 1f - offset

            start_desc2.scaleX = offset
            start_desc2.scaleY = offset

        }

        val animatorSet = AnimatorSet()
        this.animatorSet = animatorSet
        animatorSet.playSequentially(
            rotateAnimator,
            jumpAnimator,
            tranAnimator,
            alphaAnimator,
            logoAnimator
        )
        handler.postDelayed({
            animatorSet.start()
        }, 500)

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if(finish){
                    return
                }
                val intent = Intent(this@AppStartActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
    }

}