package es.parrotgames.restaurantcit.joker_library.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.parrotgames.restaurantcit.joker_library.data.entity.AdbEntity

@Dao
interface JokerDaoAdb {
    @Query("SELECT * FROM adb LIMIT 1")
    suspend fun getAdb(): AdbEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAdb(model: AdbEntity)
}