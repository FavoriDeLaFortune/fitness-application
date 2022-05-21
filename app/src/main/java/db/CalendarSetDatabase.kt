package db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CalendarSetDataEntity::class], version = 1)
abstract class CalendarSetDatabase : RoomDatabase() {
    abstract fun calendarSetDao(): CalendarSetDao
}