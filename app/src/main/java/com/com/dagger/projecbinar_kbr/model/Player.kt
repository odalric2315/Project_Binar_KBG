package com.com.dagger.projecbinar_kbr.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "nama")
    val nama: String?,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "password")
    val password: String?,

    @ColumnInfo(name = "win")
    val win: Int?,

    @ColumnInfo(name = "lose")
    val lose: Int?,

    @ColumnInfo(name = "winrate")
    val winrate: Int?
)