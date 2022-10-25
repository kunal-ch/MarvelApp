package com.kc.marvelapp.di

import android.app.Application
import androidx.room.Room
import com.kc.marvelapp.data.local.MarvelDatabase
import com.kc.marvelapp.data.remote.MarvelApi
import com.kc.marvelapp.util.Constant.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMarvelApi(): MarvelApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create(MarvelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMarvelDatabase(app: Application): MarvelDatabase {
        return Room.databaseBuilder(
            app,
            MarvelDatabase::class.java,
            "marveldb.db"
        ).build()
    }
}