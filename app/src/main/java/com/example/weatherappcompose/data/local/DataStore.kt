package com.example.weatherappcompose.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherappcompose.domain.location.Location
import com.example.weatherappcompose.domain.weather.WeatherInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("currentLocation")
        val CURRENT_LOCATION_KEY = stringPreferencesKey("current_location")
        val WEATHER_INFO_KEY = stringPreferencesKey("weather_info")
    }

    suspend fun getCurrentLocationString(): Location? {
        val json = context.dataStore.data.first()[CURRENT_LOCATION_KEY]
        Log.d("json","reading: $json")
        return Gson().fromJson(json, Location::class.java)
    }

    suspend fun saveCurrentLocationString(location: Location) {
        val json = Gson().toJson(location)
        Log.d("json","saving: $json")
        context.dataStore.edit { preferences ->
            preferences[CURRENT_LOCATION_KEY] = json
        }
    }

    suspend fun getWeatherInfoString(): WeatherInfo? {
        val json = context.dataStore.data.first()[WEATHER_INFO_KEY]
        Log.d("json","reading: $json")
        return gson.fromJson(json, WeatherInfo::class.java)
    }

    suspend fun saveWeatherInfoString(weatherInfo: WeatherInfo) {
        val json = gson.toJson(weatherInfo)
        Log.d("json","saving: $json")
        context.dataStore.edit { preferences ->
            preferences[WEATHER_INFO_KEY] = json
        }
    }

    val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter().nullSafe())
        .create()

    private class LocalDateTimeTypeAdapter: TypeAdapter<LocalDateTime>() {
        override fun write(out: JsonWriter?, value: LocalDateTime?) {
            out?.value(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value))
        }

        override fun read(input: JsonReader?): LocalDateTime {
            return LocalDateTime.parse(input?.nextString())
        }
    }
}