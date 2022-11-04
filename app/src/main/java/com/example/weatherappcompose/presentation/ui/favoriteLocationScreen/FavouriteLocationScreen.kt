package com.example.weatherappcompose.presentation.ui.favoriteLocationScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherappcompose.presentation.ViewModel
import com.example.weatherappcompose.presentation.ui.commonComposables.TopBarWithNavigateUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteLocationScreen(
    viewModel: ViewModel,
    navigateUp: () -> Unit,
) {


    Scaffold(
        topBar = { TopBarWithNavigateUp("Favorite locations", navigateUp) }
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

/*
                viewModel.state.favouriteLocationList?.let {
                    LazyColumn() {
                        items(it) { item ->
                            LocationLazyColumnItem(
                                location = item,
                                icon = Icons.Filled.Delete,
                                onClick = {
                                    viewModel.updateLocationState(item.copy(
                                        lat = Math.round(item.lat * 10000.0) / 10000.0,
                                        long = Math.round(item.long * 10000.0) / 10000.0
                                    ))
                                    navigateUp()
                                }
                            )
                        }
                    }
                }
*/
            }
        }
    }
}