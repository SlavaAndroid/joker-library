package es.parrotgames.restaurantcit.lepry_repo.bd

import es.parrotgames.restaurantcit.joker_library.data.entity.AdbEntity
import es.parrotgames.restaurantcit.joker_library.data.entity.UrlEntity


interface JokerBdRepo {
    suspend fun getUrl(): UrlEntity?
    suspend fun saveUrl(url: UrlEntity)
    suspend fun getAdb(): AdbEntity?
    suspend fun setAdb(adb: AdbEntity)
}