package db

import androidx.room.Insert
import androidx.room.Query

interface SetDao {
    @Query("SELECT * FROM set_table")
    fun getAll(): ArrayList<Set>

    @Insert
    fun insertAll(vararg set: Set)
}