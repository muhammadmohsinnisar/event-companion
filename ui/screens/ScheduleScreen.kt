package com.mohsin.eventcompanion.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mohsin.eventcompanion.data.db.SessionEntity
import com.mohsin.eventcompanion.data.repository.EventRepository
import com.mohsin.eventcompanion.viewmodel.ScheduleViewModel
import com.mohsin.eventcompanion.viewmodel.ScheduleViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(repository: EventRepository, navController: NavHostController) {
    val viewModel: ScheduleViewModel = viewModel(factory = ScheduleViewModelFactory(repository))
    val sessions = viewModel.sessions.collectAsState()

    // Initial refresh on first load
    LaunchedEffect(Unit) {
        viewModel.refreshSessions()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Schedule") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(sessions.value) { session ->
                SessionItem(session, onFavoriteClick = { viewModel.toggleFavorite(session) })
            }
        }
    }
}

@Composable
fun SessionItem(session: SessionEntity, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onFavoriteClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = session.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Time: ${session.time}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = if (session.isFavorite) "❤️ Favorite" else "🤍 Tap to favorite")
        }
    }
}
