package com.mohsin.eventcompanion.ui.screens

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
import com.mohsin.eventcompanion.data.db.SpeakerEntity
import com.mohsin.eventcompanion.data.repository.EventRepository
import com.mohsin.eventcompanion.viewmodel.SpeakersViewModel
import com.mohsin.eventcompanion.viewmodel.SpeakersViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakersScreen(repository: EventRepository) {
    val viewModel: SpeakersViewModel = viewModel(factory = SpeakersViewModelFactory(repository))
    val speakers = viewModel.speakers.collectAsState()

    // Initial refresh on first load
    LaunchedEffect(Unit) {
        viewModel.refreshSpeakers()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Speakers") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(speakers.value) { speaker ->
                SpeakerItem(speaker)
            }
        }
    }
}

@Composable
fun SpeakerItem(speaker: SpeakerEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = speaker.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = speaker.bio)
        }
    }
}
