package com.mohsin.eventcompanion.ui.event

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohsin.eventcompanion.domain.model.Event

@Composable
fun ScannedEventsScreen(
    events: List<Event>,
    onEventClick: (Event) -> Unit,
    onDeleteEvent: (Event) -> Unit
) {
    var showDialogFor by remember { mutableStateOf<Event?>(null) }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(events) { event ->
            EventListItem(
                event = event,
                onClick = { onEventClick(event) },
                onDeleteClick = { showDialogFor = event }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    showDialogFor?.let { event ->
        AlertDialog(
            onDismissRequest = { showDialogFor = null },
            title = { Text("Delete Event?") },
            text = { Text("Are you sure you want to delete \"${event.name}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteEvent(event)
                    showDialogFor = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialogFor = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Composable
fun EventListItem(
    event: Event,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onClick() },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(event.name, style = MaterialTheme.typography.titleMedium)
                Text("${event.date} • ${event.location}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}


