package com.example.weatherappcompose.di

import android.content.Context
import androidx.room.Room
import com.example.weatherappcompose.data.local.FavoriteLocationRoomDatabase
import com.example.weatherappcompose.data.remote.LocationApi
import com.example.weatherappcompose.data.remote.WeatherApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(httpClient)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideLocationApi(): LocationApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideFavoriteLocationDatabase(
        @ApplicationContext app: Context
    ): FavoriteLocationRoomDatabase {
        return Room.databaseBuilder(
            app,
            FavoriteLocationRoomDatabase::class.java,
            "favorite_location_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteLocationDao(db: FavoriteLocationRoomDatabase) = db.getFavoriteLocationDao()
}