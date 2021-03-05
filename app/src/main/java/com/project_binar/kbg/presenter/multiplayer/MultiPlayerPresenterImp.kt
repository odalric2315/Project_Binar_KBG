package com.project_binar.kbg.presenter.multiplayer

import com.project_binar.kbg.data.db.PlayerDao
import com.project_binar.kbg.ui.Multiplayer.MultiplayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MultiPlayerPresenterImp(
    private val view: MultiplayerView,
    private val playerDao: PlayerDao
) : MultiPlayerPresenter {
    override fun updateWin(win: Int, id: Int) {
        GlobalScope.launch {
            val calculate = playerDao.getSinglePlayer(id)?.win?.plus(win)
            calculate?.let { playerDao.updateWin(it, id) }
            view.showUpdatePlayer()

            updateWinRate(id)
        }
    }

    override fun updateLose(lose: Int, id: Int) {
        GlobalScope.launch {
            val calculate = playerDao.getSinglePlayer(id)?.lose?.plus(lose)
            calculate?.let { playerDao.updateLose(it, id) }
            view.showUpdatePlayer()

            updateWinRate(id)
        }
    }

    override fun updateWinRate(id: Int) {
        GlobalScope.launch {
            val playerWin = playerDao.getSinglePlayer(id)?.win.toString().toInt()
            val playerLose = playerDao.getSinglePlayer(id)?.lose.toString().toInt()

            val playerWinRate: Double = playerWin.div(
                (playerWin.plus(playerLose).times(0.100))
            ).times(10)
            playerDao.updateRate(playerWinRate, id)
            //view.showUpdatePlayer()
        }
    }
}