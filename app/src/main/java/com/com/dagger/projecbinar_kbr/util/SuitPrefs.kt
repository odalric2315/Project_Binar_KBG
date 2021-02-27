package com.com.dagger.projecbinar_kbr.util

import android.content.Context
import android.content.SharedPreferences
import com.com.dagger.projecbinar_kbr.R

class SuitPrefs(context:Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.suit_prefs), Context.MODE_PRIVATE)
    companion object {
        const val NAME="name"
        const val LOGIN="login"
    }
    var name : String?
        get() {
            return prefs.getString(NAME,"No Data")
        }
        set(value) {
            prefs.edit().putString(NAME,value).apply()
        }

    var login : Boolean
        get() {
            return prefs.getBoolean(LOGIN,false)
        }
        set(value) {
            prefs.edit().putBoolean(LOGIN,value).apply()
        }
    fun removeSharePref(key:String) {
        prefs.edit().remove(key).apply()
    }
    fun clearSharePref(){
        prefs.edit().clear().apply()
    }

}