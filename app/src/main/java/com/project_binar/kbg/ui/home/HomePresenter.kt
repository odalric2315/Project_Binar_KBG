package com.project_binar.kbg.ui.home

interface HomePresenter {
    fun goActivity(activity: Class<*>)

    fun setName(name: String)

    fun getName(): String

    fun logout()
}