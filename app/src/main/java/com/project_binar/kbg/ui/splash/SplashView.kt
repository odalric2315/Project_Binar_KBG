package com.project_binar.kbg.ui.splash

import android.graphics.drawable.Drawable

interface SplashView {
    fun showLoading(isActive: Boolean)

    fun setImageDrawable(drawable: Drawable)
}