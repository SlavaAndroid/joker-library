package es.parrotgames.restaurantcit.lepry_repo.network

import es.parrotgames.restaurantcit.network_data.NetworkData
import javax.inject.Inject

class NetworkRepoImpl @Inject constructor(private val networkData: NetworkData): NetworkRepo {

    override suspend fun getFbData(): Any? {
        return networkData.getDeep()
    }

    override suspend fun getGadid(): String {
        return networkData.getGadid()
    }
}