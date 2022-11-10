package com.example.weatherappcompose.presentation.ui.commonComposables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.presentation.ui.theme.WeatherAppComposeTheme
import com.example.weatherappcompose.R

@Composable
fun WeatherDataWithIcon(
    value: String,
    unit: String = "",
    icon: Int,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = textColor,
            //modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value$unit",
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDataWithIconPreview() {
    WeatherAppComposeTheme {
        WeatherDataWithIcon(
            value = "2.4",
            unit = "km/h, SW",
            icon = R.drawable.ic_drop
        )
    }
}