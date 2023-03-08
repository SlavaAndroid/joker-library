package es.parrotgames.restaurantcit.lepry_repo.network

import androidx.activity.ComponentActivity

interface NetworkRepo {
    suspend fun getFbData(): Any?
    suspend fun getGadid(): String
}