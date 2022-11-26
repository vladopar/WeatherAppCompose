package com.example.weatherappcompose.presentation.ui.favoriteLocationScreen

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
fun FavoriteLocationScreen(
    viewModel: ViewModel,
    backStack: ArrayDeque<NavBackStackEntry>,
    navigateUp: () -> Unit,
) {
    viewModel.loadFavoriteLocationData()

    Scaffold(
        topBar = { TopBarWithNavigateUp(
            "Favorite locations",
            backStack = backStack,
            navigateUp = navigateUp
        ) }
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

                viewModel.state.favoriteLocationList?.let {
                    LazyColumn() {
                        items(it) { item ->
                            LocationLazyColumnItem(
                                state = viewModel.state,
                                location = item,
                                icon = Icons.Filled.Delete,
                                onClick = {
                                    viewModel.updateLocationState(item)
                                    navigateUp()
                                },
                                onIconClick = {
                                    viewModel.deleteFavoriteLocation(item)
                                }
                            )
                        }
                    }
                }
                if (viewModel.state.favoriteLocationList.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(text = "Empty list",
                    modifier = Modifier
                        .weight(0.5f)
                        )
                }
            }
        }
    }
}