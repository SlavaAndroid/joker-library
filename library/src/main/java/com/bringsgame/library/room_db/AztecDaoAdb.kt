package com.bringsgame.library.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bringsgame.library.room_db.model.AdbModel

@Dao
interface AztecDaoAdb {
    @Query("SELECT * FROM adb LIMIT 1")
    suspend fun getAdb(): AdbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAdb(model: AdbModel)
}