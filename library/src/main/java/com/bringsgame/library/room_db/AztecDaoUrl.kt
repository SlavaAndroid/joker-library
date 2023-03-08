package com.bringsgame.library.room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bringsgame.library.room_db.model.UrlModel

@Dao
interface AztecDaoUrl {
    @Query("SELECT * FROM url LIMIT 1")
    suspend fun getUrl(): UrlModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUrl(model: UrlModel)
}