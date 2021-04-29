package id.chainlizard.saltiesmovie.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.chainlizard.saltiesmovie.functions.Networking
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MyDIProvider {
    @Provides
    @Singleton
    fun providerNetwork(): Networking.MyNetwork = Networking.MyNetwork()
}