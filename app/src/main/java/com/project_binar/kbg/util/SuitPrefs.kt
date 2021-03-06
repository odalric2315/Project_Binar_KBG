package com.project_binar.kbg.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project_binar.kbg.R
import com.project_binar.kbg.model.Player

class SuitPrefs(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.suit_prefs), Context.MODE_PRIVATE)
    var appSettingPrefs: SharedPreferences =
        context.getSharedPreferences("AppSettingPrefs", Context.MODE_PRIVATE)

    companion object {
        const val NAME = "name"
        const val LOGIN = "login"
        const val PLAYER = "PLAYER"
        const val DARKTHEME = "NightMode"
    }

    var name: String?
        get() {
            return prefs.getString(NAME, "No Data")
        }
        set(value) {
            prefs.edit().putString(NAME, value).apply()
        }

    var login: Boolean
        get() {
            return prefs.getBoolean(LOGIN, false)
        }
        set(value) {
            prefs.edit().putBoolean(LOGIN, value).apply()
        }

    var darktheme: Boolean
        get() {
            return prefs.getBoolean(DARKTHEME, false)
        }
        set(value) {
            prefs.edit().putBoolean(DARKTHEME, value).apply()
        }

    @SuppressLint("CommitPrefEdits")
    fun appSettingPrefs(isNightModeOn: Boolean?) {
//        val appSettingPrefs: SharedPreferences = getSharedPreferences("AppSettingPrefs", 0)
//        val sharedPrefEdit: SharedPreferences.Editor = appSettingPrefs.edit()
//        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", true)
        val prefsEditor: SharedPreferences.Editor = appSettingPrefs.edit()
        appSettingPrefs.getBoolean("NightMode", isNightModeOn!!)
        appSettingPrefs.apply{}
    }

    fun savePlayer(obj: Player?) {
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(obj)
        editor.putString(PLAYER, json)
        editor.apply()
    }

    fun getPlayer(): Player? {
        val emptyUser = Gson().toJson(Player)
        return Gson().fromJson(
            prefs.getString(PLAYER, emptyUser),
            object : TypeToken<Player>() {}.type
        )
    }

    fun removeSharePref(key: String) {
        prefs.edit().remove(key).apply()
    }

    fun clearSharePref() {
        prefs.edit().clear().apply()
    }

}


