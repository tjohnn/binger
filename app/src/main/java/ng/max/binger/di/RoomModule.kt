package ng.max.binger.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ng.max.binger.data.AppDatabase
import ng.max.binger.data.FavoriteShowDao
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    internal fun appDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun favoriteDao(appDatabase: AppDatabase): FavoriteShowDao {
        return appDatabase.favoriteDao
    }

}
