package es.parrotgames.restaurantcit.joker_library.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adb")
class AdbEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1,
    var adb: String = ""
)