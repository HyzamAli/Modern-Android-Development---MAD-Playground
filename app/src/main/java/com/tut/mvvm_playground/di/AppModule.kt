package com.tut.mvvm_playground.di

import android.content.Context
import androidx.room.Room
import com.tut.mvvm_playground.data.AppDatabase
import com.tut.mvvm_playground.network.PersonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://60fd3f631fa9e90017c70dc9.mockapi.io/testApi/"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "APP_DB"
        ).build()

    @Provides
    fun providePersonDao(appDatabase: AppDatabase) = appDatabase.PersonDao()

    @Provides
    fun providePersonRemoteKeysDao(appDatabase: AppDatabase) = appDatabase.PersonRemoteKeysDao()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun providePersonApiServices(retrofit: Retrofit): PersonApi =
        retrofit.create(PersonApi::class.java)
}