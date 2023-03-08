package es.parrotgames.restaurantcit.joker_library.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.parrotgames.restaurantcit.joker_library.data.entity.UrlEntity

@Dao
interface JokerDaoUrl {
    @Query("SELECT * FROM url LIMIT 1")
    suspend fun getUrl(): UrlEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUrl(model: UrlEntity)
}