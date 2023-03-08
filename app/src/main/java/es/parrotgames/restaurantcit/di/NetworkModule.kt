package es.parrotgames.restaurantcit.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.parrotgames.restaurantcit.network_data.NetworkData
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkData(@ApplicationContext context: Context): NetworkData {
        return NetworkData(context = context)
    }
}