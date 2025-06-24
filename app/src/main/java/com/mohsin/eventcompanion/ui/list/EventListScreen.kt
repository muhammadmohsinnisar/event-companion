package com.mohsin.eventcompanion.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohsin.eventcompanion.domain.model.Event

@Composable
fun EventListScreen(
    events: List<Event>,
    onEventClick: (Int) -> Unit,
    onDeleteEvent: (Event) -> Unit
) {
    var eventToDelete by remember { mutableStateOf<Event?>(null) }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        items(events) { event ->
            EventCard(
                event = event,
                onClick = { onEventClick(event.id) },
                onDelete = { eventToDelete = event }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    eventToDelete?.let { event ->
        AlertDialog(
            onDismissRequest = { eventToDelete = null },
            title = { Text("Delete Event") },
            text = { Text("Are you sure you want to delete \"${event.name}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteEvent(event)
                    eventToDelete = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { eventToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(event.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Date: ${event.date}", style = MaterialTheme.typography.bodySmall)
                Text("Location: ${event.location}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Event")
            }
        }
    }
}
