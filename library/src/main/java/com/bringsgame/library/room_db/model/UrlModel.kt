package com.bringsgame.library.room_db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url")
class UrlModel(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1,
    var url: String = "",
    var savedCount: Int = 1
)