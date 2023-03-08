package es.parrotgames.restaurantcit.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.parrotgames.restaurantcit.lepry_repo.bd.JokerBdRepo
import es.parrotgames.restaurantcit.lepry_repo.bd.JokerBdRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBdModule {

    @Singleton
    @Binds
    abstract fun bindJokerRepo(
        aztecBdRepoImpl: JokerBdRepoImpl
    ): JokerBdRepo
}