package com.mohsin.eventcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mohsin.eventcompanion.data.db.SpeakerEntity
import com.mohsin.eventcompanion.data.repository.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SpeakersViewModel(private val repository: EventRepository) : ViewModel() {

    val speakers: StateFlow<List<SpeakerEntity>> = repository.speakersFlow
        .map { it.sortedBy { speaker -> speaker.name } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun refreshSpeakers() {
        viewModelScope.launch {
            try {
                repository.refreshSpeakers()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

class SpeakersViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpeakersViewModel::class.java)) {
            return SpeakersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
