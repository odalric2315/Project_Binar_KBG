package com.project_binar.kbg.presenter.multiplayer

import android.util.Log
import com.project_binar.kbg.data.db.PlayerDao
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.ui.Multiplayer.MultiplayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MultiplayerPresenterImp(private val view: MultiplayerView, private val playerDao: PlayerDao) :
    MultiplayerPresenter {
    override fun updateWin(win: Int, id: Int) {
        Log.e("win","$win, $id")
        GlobalScope.launch {
            playerDao.updateWin(id, win)
            view.showUpdatePlayer()
        }
    }

    override fun updateLose(lose: Int, id: Int) {
        Log.e("lose","$lose, $id")
        GlobalScope.launch {
            playerDao.updateWin(id, lose)
            view.showUpdatePlayer()
            getSinglePlayer(id)
        }
    }

    override fun updateWinrate(winrate: Float, id: Int) {
        Log.e("winrate","$winrate, $id")
        GlobalScope.launch {
            playerDao.updateWinrate(winrate, id)
            view.showUpdatePlayer()
        }
    }

    override fun getSinglePlayer(id: Int) {
        GlobalScope.launch {
            val dataPlayer = playerDao.getSinglePlayer(id)
            dataPlayer?.let { view.showDataPlayer(it) }
        }
    }

    override fun updatePlayerAll(player: Player) {
        GlobalScope.launch {
            playerDao.updatePlayerAll(player)
            view.showUpdatePlayer()
        }
    }

}