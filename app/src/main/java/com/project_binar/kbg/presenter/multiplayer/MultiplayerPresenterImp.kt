package com.project_binar.kbg.presenter.multiplayer

import com.project_binar.kbg.data.db.PlayerDao
import com.project_binar.kbg.ui.Multiplayer.MultiplayerActivity
import com.project_binar.kbg.ui.Multiplayer.MultiplayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MultiplayerPresenterImp(private val view: MultiplayerView, private val playerDao: PlayerDao):MultiplayerPresenter{
    override fun updateWin(win: Int, id: Int) {
        GlobalScope.launch {
            playerDao.updateWin(id,win)
            view.showUpdatePlayer()
        }
    }

    override fun updateLose(lose: Int, id: Int) {
        GlobalScope.launch {
            playerDao.updateWin(id,lose)
            view.showUpdatePlayer()
        }
    }

    override fun updateWinrate(winrate: Int, id: Int) {
        GlobalScope.launch {
            playerDao.updateWin(id,winrate)
            view.showUpdatePlayer()
        }
    }

}