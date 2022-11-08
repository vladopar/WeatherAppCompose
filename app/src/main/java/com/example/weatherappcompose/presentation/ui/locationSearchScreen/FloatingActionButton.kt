package com.example.weatherappcompose.presentation.ui.locationSearchScreen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.weatherappcompose.domain.location.Location
import com.example.weatherappcompose.presentation.ViewModel
import com.google.android.gms.location.LocationServices
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun LocationPermission(
    viewModel: ViewModel,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current


    fun getPositionAndNavigateUp() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
            context)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { gpsLocation ->
            if (gpsLocation != null) {
                val lat = BigDecimal(gpsLocation.latitude).setScale(2, RoundingMode.HALF_UP).toDouble()
                val lon = BigDecimal(gpsLocation.longitude).setScale(2, RoundingMode.HALF_UP).toDouble()
                viewModel.storePositionAsLocation(
                    Location(
                        id = null,
                        name = "GPS position: $lat",
                        lat = lat,
                        lon = lon,
                        countryCode = "$lon",
                        region = null
                    )
                )
            }
        }
        navigateUp()
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            getPositionAndNavigateUp()
        } else {

        }
    }

    FloatingActionButton(onClick = {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                getPositionAndNavigateUp()
            }
            else -> {
                launcher.launch(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                )
            }
        }
    }) {
        Icon(imageVector = Icons.Filled.GpsFixed, contentDescription = "Get position")
    }
}