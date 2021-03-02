package com.project_binar.kbg.ui.splash

import android.app.Activity
import android.content.Intent
import androidx.core.content.ContextCompat
import com.project_binar.kbg.R
import com.project_binar.kbg.ui.home.HomeActivity
import com.project_binar.kbg.ui.lending_page.LendingPageActivity
import com.project_binar.kbg.util.SuitPrefs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashContractImpl(
    private val context: Activity,
    private val splashView: SplashContract.SplashView
) :
    SplashContract.Presenter {
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
}