package es.parrotgames.restaurantcit.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.parrotgames.restaurantcit.lepry_repo.network.NetworkRepo
import es.parrotgames.restaurantcit.lepry_repo.network.NetworkRepoImpl

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryNetworkModule {

    @Binds
    abstract fun bindsNetworkRepo(repo: NetworkRepoImpl): NetworkRepo
}