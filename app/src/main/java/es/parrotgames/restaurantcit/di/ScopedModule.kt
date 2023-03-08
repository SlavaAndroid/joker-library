package es.parrotgames.restaurantcit.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object ScopedModule {

    @Provides
    fun provideAdScope() = CoroutineScope(Dispatchers.IO)
}