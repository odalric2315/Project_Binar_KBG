package com.project_binar.kbg.presenter.leaderboard

import com.project_binar.kbg.data.db.PlayerDao
import com.project_binar.kbg.model.Player
import com.project_binar.kbg.ui.leaderboard.LeaderBoardView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeaderBoardPresenterImp(private val view: LeaderBoardView, private val playerDao: PlayerDao) :
    LeaderBoardPresenter {
    override fun getData() {
        GlobalScope.launch {
            var players = playerDao.getAllPlayer()
            players = sortData(players)
            view.showData(players!!)
        }
    }

    override fun sortData(players: List<Player>?): List<Player>? {
        //gimana cara sort players: List<Player> berdasarkan win rate nya?
        //abis players di sort di return lagi
        return players?.sortedByDescending { player -> player.rate }
    }


}