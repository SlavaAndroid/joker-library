package es.parrotgames.restaurantcit.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.parrotgames.restaurantcit.joker_library.data.bd.JokerDataBase
import es.parrotgames.restaurantcit.joker_library.data.dao.JokerDaoAdb
import es.parrotgames.restaurantcit.joker_library.data.dao.JokerDaoUrl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object  RoomModule {

    @Singleton
    @Provides
    fun provideJokerDb(@ApplicationContext context: Context): JokerDataBase {
        return Room
            .databaseBuilder(
                context,
                JokerDataBase::class.java,
                JokerDataBase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideAdbDAO(lepryDataBase: JokerDataBase): JokerDaoAdb {
        return lepryDataBase.getAdbDao()
    }

    @Singleton
    @Provides
    fun provideUrlDAO(lepryDataBase: JokerDataBase): JokerDaoUrl {
        return lepryDataBase.getUrlDao()
    }
}