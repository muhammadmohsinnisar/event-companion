package com.mohsin.eventcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsin.eventcompanion.data.db.SessionEntity
import com.mohsin.eventcompanion.data.repository.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repository: EventRepository) : ViewModel() {

    val sessions: StateFlow<List<SessionEntity>> = repository.sessionsFlow
        .map { it.sortedBy { session -> session.time } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun refreshSessions() {
        viewModelScope.launch {
            try {
                repository.refreshSessions()
            } catch (e: Exception) {
                e.printStackTrace() // You can add error handling here
            }
        }
    }

    fun toggleFavorite(session: SessionEntity) {
        viewModelScope.launch {
            repository.updateSession(
                session.copy(isFavorite = !session.isFavorite)
            )
        }
    }
}
