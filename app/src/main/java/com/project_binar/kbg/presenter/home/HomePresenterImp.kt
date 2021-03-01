package com.project_binar.kbg.presenter.home

import com.project_binar.kbg.data.db.PlayerDao
import com.project_binar.kbg.ui.home.HomeView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomePresenterImp(private val view : HomeView, private val playerDao: PlayerDao):
    HomePresenter {
    override fun getSinglePlayer(id: Int) {
        GlobalScope.launch {
            val player1 = playerDao.getSinglePlayer(id)
            view.viewPlayer(player1)
        }
    }

}