package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherappcompose.ui.theme.WeatherAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppComposeTheme {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    WeatherAppComposeTheme() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = MaterialTheme.colors.surface)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_sunny),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.LightGray),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
