package ru.tvhelp.akruglov.testexpandable

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

fun slideDown(ctx: Context, v: View) {
    val a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down)
    a.reset()
    v.clearAnimation()
    v.startAnimation(a)
}

fun slideUp(ctx: Context, v: View) {
    val a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up)
    a.reset()
    v.clearAnimation()
    v.startAnimation(a)
}