package db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SetDataEntity::class], version = 1)
abstract class SetDatabase: RoomDatabase() {
    abstract fun setDao(): SetDao
}