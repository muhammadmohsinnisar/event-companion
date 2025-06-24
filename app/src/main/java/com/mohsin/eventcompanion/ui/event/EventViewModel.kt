package com.mohsin.eventcompanion.ui.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsin.eventcompanion.data.repository.EventRepository
import com.mohsin.eventcompanion.domain.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class EventViewModel : ViewModel() {
    private val repository = EventRepository()

    private val _uiState = MutableLiveData<EventUiState>(EventUiState.Idle)
    val uiState: LiveData<EventUiState> = _uiState

    fun setEvent(event: Event) {
        _uiState.value = EventUiState.Success(event)
    }

    fun loadEventFromUrl(url: String, onComplete: (Event?) -> Unit = {}) {
        _uiState.value = EventUiState.Loading
        viewModelScope.launch {
            try {
                val event = withContext(Dispatchers.IO) {
                    repository.fetchEventFromUrl(url)
                }
                _uiState.value = EventUiState.Success(event)
                onComplete(event)
            } catch (e: IOException) {
                _uiState.value = EventUiState.Error("Failed to load event: ${e.message}")
                onComplete(null)
            } catch (e: Exception) {
                _uiState.value = EventUiState.Error("Parsing error: ${e.message}")
                onComplete(null)
            }
        }
    }

    fun toggleFavorite(sessionId: Int) {
        val currentState = _uiState.value
        if (currentState is EventUiState.Success) {
            val updatedSessions = currentState.event.sessions.map {
                if (it.id == sessionId) it.copy(isFavorite = !it.isFavorite) else it
            }
            val updatedEvent = currentState.event.copy(sessions = updatedSessions)
            _uiState.value = EventUiState.Success(updatedEvent)
        }
    }
}
