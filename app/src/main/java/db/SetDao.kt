package db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SetDao {
    @Query("SELECT * FROM set_table")
    suspend fun getAll(): List<SetDataEntity>

    @Query("SELECT * FROM set_table WHERE name IN (:setName)")
    suspend fun getByName(setName: String): List<SetDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg set: SetDataEntity)
}