package com.example.weatherappcompose.domain.weather

import java.math.RoundingMode

class WindDirectionMapper {
    //converts wind direction from degrees in Int to String
    companion object {
        fun map(windDirectionDouble: Int): String {
            return when (windDirectionDouble.toBigDecimal().setScale(0, RoundingMode.HALF_UP)
                .toDouble()) {
                in 23.0..67.0 -> "NE"
                in 68.0..112.0 -> "E"
                in 113.0..157.0 -> "SE"
                in 158.0..202.0 -> "S"
                in 203.0..247.0 -> "SW"
                in 248.0..292.0 -> "W"
                in 293.0..337.0 -> "NW"
                else -> "N"
            }
        }
    }
}