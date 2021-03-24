package com.project_binar.kbg.presenter.leaderboard

import com.project_binar.kbg.model.Player

interface LeaderBoardPresenter {
    fun getData()
    fun sortData(players: List<Player>?) : List<Player>?
}