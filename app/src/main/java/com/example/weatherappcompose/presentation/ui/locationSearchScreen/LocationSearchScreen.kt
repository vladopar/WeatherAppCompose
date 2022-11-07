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
import androidx.navigation.NavBackStackEntry
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.commonComposables.LocationLazyColumnItem
import com.example.weatherappcompose.presentation.ui.commonComposables.TopBarWithNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSearchScreen(
    viewModel: ViewModel,
    backStack: ArrayDeque<NavBackStackEntry>,
    navigateUp: () -> Unit,
) {
    var textFieldState by remember { mutableStateOf("") }

    val trailingIcon = @Composable {
        IconButton(onClick = {
            textFieldState = ""
            viewModel.loadListOfLocationData(textFieldState)
        }) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Delete text",
            )
        }
    }

    Log.d("backstack", backStack.size.toString())

    viewModel.loadListOfLocationData(textFieldState)

    Scaffold(
        topBar = {
            TopBarWithNavigateUp(
                "Search location",
                backStack = backStack,
                navigateUp = navigateUp
            )
        },

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
                        viewModel.loadListOfLocationData(textFieldState)
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
                                    viewModel.updateLocationState(
                                        item.copy(
                                            lat = Math.round(item.lat * 10000.0) / 10000.0,
                                            lon = Math.round(item.lon * 10000.0) / 10000.0
                                        )
                                    )
                                    navigateUp()
                                },
                                onIconClick = {
                                    viewModel.insertFavoriteLocation(
                                        item.copy(
                                            lat = Math.round(item.lat * 10000.0) / 10000.0,
                                            lon = Math.round(item.lon * 10000.0) / 10000.0
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

