package com.com.dagger.projecbinar_kbr.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.com.dagger.projecbinar_kbr.model.Player

@Dao
interface PlayerDao {
    @Insert
    fun addPlayer(player: Player): Long

    @Query("SELECT * FROM player")
    fun getListPlayer(): List<Player>?

    @Query("UPDATE player SET win = :win WHERE id = :id")
    fun updateWin(win: Int, id: Int) : Int

    @Query("UPDATE player SET lose = :lose WHERE id = :id")
    fun updateLose(lose: Int,id: Int) : Int

    @Query("UPDATE player SET winrate = :winrate WHERE id = :id")
    fun updateWinrate(winrate: Int, id: Int) : Int

    @Query("DELETE FROM player WHERE id = :id")
    fun deleteNote(id: Int) : Int
}