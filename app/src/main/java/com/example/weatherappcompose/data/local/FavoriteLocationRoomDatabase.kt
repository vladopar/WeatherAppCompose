package com.example.weatherappcompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherappcompose.domain.location.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class FavoriteLocationRoomDatabase : RoomDatabase() {

    abstract fun getFavoriteLocationDao(): FavoriteLocationDao

}