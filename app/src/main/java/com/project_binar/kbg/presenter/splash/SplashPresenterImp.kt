@file:Suppress("DEPRECATION")

package com.project_binar.kbg.presenter.splash

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.project_binar.kbg.R
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.lending_page.LendingPageActivity
import com.project_binar.kbg.ui.splash.SplashView
import com.project_binar.kbg.util.SuitPrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashPresenterImp(
    private val context: Activity,
    private val splashView: SplashView
) : SplashPresenter {
    private val prefSuitPrefs = SuitPrefs(context)

    override fun checkStatus() {
        GlobalScope.launch {
            delay(2000)
            if (prefSuitPrefs.login) {
                context.startActivity(Intent(context, HomeActivity::class.java))
            } else context.startActivity(Intent(context, LendingPageActivity::class.java))

            context.finish()
        }

        splashView.showLoading(true)

        ContextCompat.getDrawable(context, R.drawable.img_gametitle)?.let {
            splashView.setImageDrawable(it)
        }
    }

    override fun getActiveTheme() {
        if (prefSuitPrefs.darktheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}

