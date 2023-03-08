package es.parrotgames.restaurantcit.lepry_repo.bd


import es.parrotgames.restaurantcit.joker_library.data.dao.JokerDaoAdb
import es.parrotgames.restaurantcit.joker_library.data.dao.JokerDaoUrl
import es.parrotgames.restaurantcit.joker_library.data.entity.AdbEntity
import es.parrotgames.restaurantcit.joker_library.data.entity.UrlEntity
import javax.inject.Inject

class JokerBdRepoImpl @Inject constructor(private val lepryDaoAdb: JokerDaoAdb, private val lepryDaoUrl: JokerDaoUrl) :
    JokerBdRepo {
    override suspend fun getUrl(): UrlEntity? {
        return lepryDaoUrl.getUrl()
    }

    override suspend fun saveUrl(url: UrlEntity) {
        lepryDaoUrl.saveUrl(model = url)
    }

    override suspend fun getAdb(): AdbEntity? {
        return lepryDaoAdb.getAdb()
    }

    override suspend fun setAdb(adb: AdbEntity) {
        lepryDaoAdb.setAdb(model = adb)
    }
}