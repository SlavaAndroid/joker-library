package com.bringsgame.library.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bringsgame.library.room_db.model.AdbModel
import com.bringsgame.library.room_db.model.UrlModel

@Database(entities = [UrlModel::class, AdbModel::class], version = 1, exportSchema = false)
abstract class AztecDataBase : RoomDatabase() {
    abstract fun getUrlDao(): AztecDaoUrl
    abstract fun getAdbDao(): AztecDaoAdb

    companion object{
        val DATABASE_NAME: String = "aztec_db"
    }
}