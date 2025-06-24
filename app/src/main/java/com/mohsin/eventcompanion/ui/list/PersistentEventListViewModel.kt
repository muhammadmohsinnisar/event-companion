package com.mohsin.eventcompanion.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mohsin.eventcompanion.data.repository.RoomEventRepository
import com.mohsin.eventcompanion.domain.model.Event
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PersistentEventListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RoomEventRepository(application)

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _events.value = repository.getAllEvents()
        }
    }

    fun addEvent(event: Event) {
        viewModelScope.launch {
            repository.saveEvent(event)
            loadEvents()
        }
    }

    fun getEventById(id: Int): Event? = _events.value.find { it.id == id }

    fun deleteEvent(event: Event) {
        viewModelScope.launch {
            repository.deleteEvent(event)
            _events.value = repository.getAllEvents()
        }
    }

}
