package com.example.bunonbasket.utils.helpers

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager

/**
 * Created by inan on 25/5/21
 */

fun slideView(
    view: View,
    currentHeight: Int,
    newHeight: Int
) {
    val slideAnimator: ValueAnimator = ValueAnimator
        .ofInt(currentHeight, newHeight)
        .setDuration(500)

    slideAnimator.addUpdateListener { animation1 ->
        val value = animation1.animatedValue as Int
        view.layoutParams.height = value
        view.requestLayout()
    }

    val animationSet = AnimatorSet()
    animationSet.interpolator = AccelerateDecelerateInterpolator()
    animationSet.play(slideAnimator)
    animationSet.start()
}

