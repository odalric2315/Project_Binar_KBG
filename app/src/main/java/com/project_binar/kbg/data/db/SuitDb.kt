package com.project_binar.kbg.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project_binar.kbg.R
import com.project_binar.kbg.model.Player

@Database(entities = [Player::class], version = 1, exportSchema = false)
abstract class SuitDb: RoomDatabase() {
    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: SuitDb? = null
        fun getInstance(context: Context): SuitDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SuitDb::class.java,
                    context.getString(R.string.db_name)
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}