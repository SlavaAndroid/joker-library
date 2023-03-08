package com.bringsgame.library.room_db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "adb")
class AdbModel(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1,
    var adb: String = ""
)