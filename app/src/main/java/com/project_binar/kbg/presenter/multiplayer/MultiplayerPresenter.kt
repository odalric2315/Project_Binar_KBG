package com.project_binar.kbg.presenter.multiplayer

import com.project_binar.kbg.model.Player

interface MultiplayerPresenter {
    fun updateWin(win: Int, id: Int)
    fun updateLose(lose: Int, id: Int)
    fun updateWinrate(winrate: Float, id: Int)

    fun getSinglePlayer(id:Int)
    fun updatePlayerAll(player: Player)

}