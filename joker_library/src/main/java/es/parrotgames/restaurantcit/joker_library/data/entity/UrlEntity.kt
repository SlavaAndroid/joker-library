package es.parrotgames.restaurantcit.joker_library.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url")
class UrlEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1,
    var url: String = "",
    var savedCount: Int = 1
)