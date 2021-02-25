package com.com.dagger.projecbinar_kbr.util

import android.content.Context
import android.content.SharedPreferences
import com.com.dagger.projecbinar_kbr.R

class SuitPrefs(context:Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.suit_prefs), Context.MODE_PRIVATE)


}