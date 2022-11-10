package com.example.weatherappcompose.presentation.ui.mainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherappcompose.R
import com.example.weatherappcompose.presentation.UiState
import com.example.weatherappcompose.presentation.ui.commonComposables.WeatherDataWithIcon
import java.time.format.DateTimeFormatter

@Composable
fun WeatherCard(
    state: UiState,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .padding(16.dp)
                .shadow(4.dp, RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${data.time.format(DateTimeFormatter.ISO_DATE)}," +
                            " ${data.time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                    modifier = Modifier
                        .align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = state.currentSelectedLocation?.name + ", " + (state.currentSelectedLocation?.countryCode?.uppercase()
                        ?: ""),
                    modifier = Modifier
                        .align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(id = data.weatherType.iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(180.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperature} °C",
                    fontSize = 50.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = data.weatherType.weatherDesc,
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataWithIcon(
                        value = data.sunrise,
                        icon = R.drawable.ic_sunrise
                    )
                    WeatherDataWithIcon(
                        value = data.sunset,
                        icon = R.drawable.ic_sunset
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataWithIcon(
                        value = data.windSpeed.toString(),
                        unit = " km/h, ${data.windDirection}",
                        icon = R.drawable.ic_wind
                    )
                    WeatherDataWithIcon(
                        value = data.humidity.toString(),
                        unit = "%",
                        icon = R.drawable.ic_humidity
                    )
                    WeatherDataWithIcon(
                        value = data.pressure.toString(),
                        unit = " hPa",
                        icon = R.drawable.ic_pressure
                    )
                }
            }
        }
    }
}