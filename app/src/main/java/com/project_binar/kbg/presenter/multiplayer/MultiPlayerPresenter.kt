package com.project_binar.kbg.presenter.multiplayer

interface MultiplayerPresenter {
    fun updateWin(win: Int, id: Int)
    fun updateLose(lose: Int, id: Int)
    fun updateWinRate(id: Int)
}