package com.mohsin.eventcompanion.ui.session

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mohsin.eventcompanion.domain.model.SessionWithSpeaker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailScreen(
    session: SessionWithSpeaker?,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(session?.title ?: "Session Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        session?.let {
            Column(modifier = Modifier
                .padding(padding)
                .padding(16.dp)) {
                Text(it.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("${it.startTime} – ${it.endTime}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(it.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Speaker: ${it.speaker.name}", style = MaterialTheme.typography.titleMedium)
                Text(it.speaker.bio, style = MaterialTheme.typography.bodySmall)
            }
        } ?: Text("Session not found", modifier = Modifier.padding(16.dp))
    }
}
