package com.example.weatherappcompose.di

import com.example.weatherappcompose.data.locationTracker.DefaultLocationTracker
import com.example.weatherappcompose.domain.locationTracker.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationtracker: DefaultLocationTracker): LocationTracker
}