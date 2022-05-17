package db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set_table")
class SetDataEntity {
    @PrimaryKey(autoGenerate = true) val id: Int,
    val set: Set
}