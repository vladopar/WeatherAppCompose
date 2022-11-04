package com.example.weatherappcompose.presentation.ui.locationSearchScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.commonComposables.LocationLazyColumnItem
import com.example.weatherappcompose.presentation.ui.commonComposables.TopBarWithNavigateUp
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchScreen(
    viewModel: ViewModel,
    navigateUp: () -> Unit,
) {
    var textFieldState by remember { mutableStateOf("") }

    var job: Job? = null

    val trailingIcon = @Composable {
        IconButton(onClick = {
            textFieldState = ""
            viewModel.loadLocationData(textFieldState)
        }) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Delete text",
            )
        }
    }

    viewModel.loadLocationData(textFieldState)

    Scaffold(
        topBar = { TopBarWithNavigateUp("Search location", navigateUp) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = textFieldState,
                    onValueChange = { text ->
                        textFieldState = text
                        job?.cancel()
                        job = MainScope().launch {
                            delay(400)
                            Log.d("xxx",textFieldState.trim())
                            viewModel.loadLocationData(textFieldState.trim())
// TODO trim() not working
                        }
                    },
                    singleLine = true,
                    label = { Text(text = "Search location") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    },
                    trailingIcon = if (textFieldState.isNotBlank()) trailingIcon
                    else null,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                viewModel.state.locationList?.let {
                    LazyColumn() {
                        items(it) { item ->
                            LocationLazyColumnItem(
                                location = item,
                                icon = Icons.Filled.Favorite,
                                onClick = {
                                    viewModel.updateLocationState(item.copy(
                                        lat = Math.round(item.lat * 10000.0) / 10000.0,
                                        lon = Math.round(item.lon * 10000.0) / 10000.0
                                    ))
                                    navigateUp()
                                },
                                onIconClick = {
                                    viewModel.insertFavoriteLocation(item.copy(
                                        lat = Math.round(item.lat * 10000.0) / 10000.0,
                                        lon = Math.round(item.lon * 10000.0) / 10000.0
                                    ))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

