package com.example.weatherappcompose.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherappcompose.domain.location.Location
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class CurrentLocationDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("currentLocation")
        val CURRENT_LOCATION_KEY = stringPreferencesKey("current_location")
    }

    suspend fun getCurrentLocationString(): Location? {
        val locationJson = context.dataStore.data.first()[CURRENT_LOCATION_KEY]
        Log.d("json","reading: $locationJson")
        return Gson().fromJson(locationJson, Location::class.java)
    }

    suspend fun saveCurrentLocationString(location: Location) {
        val locationJson = Gson().toJson(location)
        Log.d("json","saving: $locationJson")
        context.dataStore.edit { preferences ->
            preferences[CURRENT_LOCATION_KEY] = locationJson
        }
    }
}