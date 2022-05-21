package db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CalendarSetDao {
    @Query("SELECT * FROM calendar_set_table")
    suspend fun getAll(): List<CalendarSetDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg set: CalendarSetDataEntity)
}