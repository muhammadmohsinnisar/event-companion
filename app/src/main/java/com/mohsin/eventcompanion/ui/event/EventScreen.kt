package com.mohsin.eventcompanion.ui.event

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohsin.eventcompanion.domain.model.Event
import com.mohsin.eventcompanion.domain.model.SessionWithSpeaker

@Composable
fun EventScreen(viewModel: EventViewModel, onSessionClick: (Int) -> Unit) {
    val state by viewModel.uiState.observeAsState(EventUiState.Idle)

    when (state) {
        is EventUiState.Success -> {
            val event = (state as EventUiState.Success).event
            EventDetails(event, onSessionClick, viewModel::toggleFavorite)
        }

        is EventUiState.Error -> Text((state as EventUiState.Error).message)
        is EventUiState.Loading -> CircularProgressIndicator()
        EventUiState.Idle -> {}
    }
}

@Composable
fun EventDetails(
    event: Event,
    onSessionClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text(event.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(8.dp))
            Text("Date: ${event.date}", style = MaterialTheme.typography.bodyMedium)
            Text("Location: ${event.location}", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text(event.description, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(16.dp))
            Text("Sessions:", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
        }

        items(event.sessions) { session ->
            SessionCard(session = session, onClick = {
                onSessionClick(session.id)
            }, onToggleFavorite = {
                onToggleFavorite(session.id)
            })
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun SessionCard(session: SessionWithSpeaker, onClick: () -> Unit, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    session.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (session.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Toggle Favorite"
                    )
                }
            }
            Text(
                "${session.startTime} – ${session.endTime}",
                style = MaterialTheme.typography.labelSmall
            )
            Spacer(Modifier.height(8.dp))
            Text(session.description, style = MaterialTheme.typography.bodySmall)
            Text("Speaker: ${session.speaker.name}", style = MaterialTheme.typography.labelSmall)
        }
    }

}
