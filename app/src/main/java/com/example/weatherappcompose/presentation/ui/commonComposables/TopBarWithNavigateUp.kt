package com.example.weatherappcompose.presentation.ui.commonComposables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithNavigateUp(
    title: String,
    backStack: ArrayDeque<NavBackStackEntry> = ArrayDeque(),
    navigateUp: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (backStack.size > 2) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}