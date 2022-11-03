package com.example.weatherappcompose.presentation.ui.locationSearchScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.presentation.LocationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchScreen(
    locationViewModel: LocationViewModel,
    navigateUp: () -> Unit
) {
    var textFieldState by remember { mutableStateOf("") }

    Scaffold(
        topBar = { LocationSearchTopBar(navigateUp) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = textFieldState,
                    onValueChange = { text ->
                        textFieldState = text
                        locationViewModel.loadLocationData(textFieldState)
                    },
                    singleLine = true,
                    label = { Text(text = "Search location") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            textFieldState = ""
                            locationViewModel.loadLocationData(textFieldState)
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Delete text"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (locationViewModel.state.locationList != null) {
                        for (item in locationViewModel.state.locationList!!) {
                            Text(
                                text = item.name,
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchTopBar(
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Search Location") },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}