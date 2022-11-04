package com.example.weatherappcompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherappcompose.domain.location.Location

@Dao
interface FavoriteLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteLocation(location: Location)

    @Delete
    suspend fun deleteFavoriteLocation(location: Location)

    @Query("SELECT * FROM favoritelocations")
    suspend fun getFavoriteLocations(): List<Location>
}