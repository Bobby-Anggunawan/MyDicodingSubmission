package id.chainlizard.saltiesmovie.data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.chainlizard.saltiesmovie.functions.MyDatabase
import id.chainlizard.saltiesmovie.functions.Networking
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MyDIProvider {
    @Provides
    @Singleton
    fun providerNetwork(): Networking.MyNetwork = Networking.MyNetwork()

    @Provides
    @Singleton
    fun providerDatabase(@ApplicationContext context: Context): MyDatabase.AppDatabase = Room.databaseBuilder(
        context,
        MyDatabase.AppDatabase::class.java, "SaltiesMovieDB"
    ).build()

}