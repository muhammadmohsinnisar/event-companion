package com.mohsin.eventcompanion

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.lifecycle.viewmodel.compose.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohsin.eventcompanion.ui.event.EventScreen
import com.mohsin.eventcompanion.ui.event.EventViewModel
import com.mohsin.eventcompanion.ui.event.ScannedEventsScreen
import com.mohsin.eventcompanion.ui.list.PersistentEventListViewModel
import com.mohsin.eventcompanion.ui.qr.TicketScannerScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                AppDrawerNavigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerNavigation() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val eventViewModel: EventViewModel = viewModel()
    val eventListViewModel: PersistentEventListViewModel = viewModel(
        factory = PersistentEventListViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                DrawerDestination.all.forEach { dest ->
                    NavigationDrawerItem(
                        label = { Text(dest.label) },
                        selected = navController.currentDestination?.route == dest.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(dest.route) {
                                launchSingleTop = true
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Event Companion") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "event_list",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(DrawerDestination.Scanner.route) {
                    TicketScannerScreen(
                        navController = navController,
                        onEventFetched = { url ->
                            eventViewModel.loadEventFromUrl(url) {
                                it?.let { event ->
                                    eventListViewModel.addEvent(event)
                                    navController.navigate("event_list")
                                }
                            }
                        }
                    )
                }

                composable("event_list") {
                    val events by eventListViewModel.events.collectAsState()
                    ScannedEventsScreen(
                        events = events,
                        onEventClick = { event ->
                            eventViewModel.setEvent(event)
                            navController.navigate("event_display")
                        },
                        onDeleteEvent = { event ->
                            eventListViewModel.deleteEvent(event)
                        }
                    )
                }

                composable("event_display") {
                    EventScreen(viewModel = eventViewModel, onSessionClick = { /* TODO */ })
                }

                composable(DrawerDestination.Favorites.route) {
                    Text("Favorites screen coming soon", modifier = Modifier.padding(24.dp))
                }

                composable(DrawerDestination.About.route) {
                    Text("This is the Event Companion app.", modifier = Modifier.padding(24.dp))
                }
            }
        }
    }
}

