package com.mohsin.eventcompanion.ui.session

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mohsin.eventcompanion.domain.model.SessionWithSpeaker
import com.mohsin.eventcompanion.notifications.scheduleSessionReminder

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailScreen(
    session: SessionWithSpeaker?,
    onBack: () -> Unit
) {
    val context = LocalContext.current

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

                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    scheduleSessionReminder(context, it)
                }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Remind Me")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Remind Me")
                }
            }
        } ?: Text("Session not found", modifier = Modifier.padding(16.dp))
    }
}
