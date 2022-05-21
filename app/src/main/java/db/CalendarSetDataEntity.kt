package db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "calendar_set_table")
data class CalendarSetDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: String,
    val name: String,
    val time: String
    )