package com.mohsin.eventcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mohsin.eventcompanion.data.db.EventDatabase
import com.mohsin.eventcompanion.data.repository.EventRepository
import com.mohsin.eventcompanion.ui.screens.*
import com.mohsin.eventcompanion.ui.theme.EventcompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = EventDatabase.getDatabase(this)
        val repository = EventRepository(db.sessionDao(), db.speakerDao())

        setContent {
            EventcompanionTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController, currentRoute = currentRoute)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "schedule",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("schedule") {
                            ScheduleScreen(repository = repository, navController)
                        }
                        composable("speakers") {
                            SpeakersScreen(repository = repository)
                        }
                        composable("ticket") {
                            TicketScreen()
                        }
                        composable("scanner") {
                            TicketScannerScreen(navController)
                        }
                        composable("qrDetails/{qrData}") { backStackEntry ->
                            val qrData = backStackEntry.arguments?.getString("qrData") ?: ""
                            QRCodeDetailsScreen(qrData)
                        }
                    }
                }
            }
        }
    }
}
