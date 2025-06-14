package com.mohsin.eventcompanion.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    val items = listOf(
        BottomNavItem("Schedule", "schedule", Icons.Filled.Home),
        BottomNavItem("Speakers", "speakers", Icons.Filled.Person),
        BottomNavItem("Ticket", "ticket", Icons.Filled.Info),
        BottomNavItem("Scanner", "scanner", Icons.TwoTone.Add)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)
