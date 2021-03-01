package com.project_binar.kbg.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.project_binar.kbg.model.Player

@Dao
interface PlayerDao {
    @Insert
    fun addPlayer(player: Player): Long

    /*refactor name from getListPlayer to loginPlayer*/
    @Query("SELECT * FROM player WHERE username = :username AND password= :password LIMIT 1")
    fun loginPlayer(username: String, password: String): Player?

    @Query("UPDATE player SET win = :win WHERE id = :id")
    fun updateWin(win: Int, id: Int): Int

    @Query("UPDATE player SET lose = :lose WHERE id = :id")
    fun updateLose(lose: Int, id: Int): Int

    @Query("UPDATE player SET winrate = :win/(:win+:lose) WHERE id = :id")
    fun updateWinrate(winrate: Int, win: Int, lose: Int, id: Int): Int

    @Query("DELETE FROM player WHERE id = :id")
    fun deleteNote(id: Int): Int

    @Query("SELECT * FROM player WHERE id = :id")
    fun getSinglePlayer(id: Int): Player?

    @Query("UPDATE player SET nama = :nama WHERE id = :id")
    fun updateNamePlayer(id: Int, nama: String): Int

    @Query("SELECT * FROM player")
    fun getAllPlayer():MutableList<Player>

}