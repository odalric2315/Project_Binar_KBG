package com.project_binar.kbg.ui.leaderboard

import com.project_binar.kbg.model.Player

interface LeaderboarView {
    fun showData(data: List<Player>)
}