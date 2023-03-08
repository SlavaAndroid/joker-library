package es.parrotgames.restaurantcit.joker_library.data.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import es.parrotgames.restaurantcit.joker_library.data.dao.JokerDaoAdb
import es.parrotgames.restaurantcit.joker_library.data.dao.JokerDaoUrl
import es.parrotgames.restaurantcit.joker_library.data.entity.AdbEntity
import es.parrotgames.restaurantcit.joker_library.data.entity.UrlEntity


@Database(entities = [UrlEntity::class, AdbEntity::class], version = 1, exportSchema = false)
abstract class JokerDataBase : RoomDatabase() {
    abstract fun getUrlDao(): JokerDaoUrl
    abstract fun getAdbDao(): JokerDaoAdb

    companion object{
        val DATABASE_NAME: String = "joker_db"
    }
}