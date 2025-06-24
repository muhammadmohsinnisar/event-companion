package com.mohsin.eventcompanion.ui.event

import com.mohsin.eventcompanion.domain.model.Event

sealed class EventUiState {
    object Idle : EventUiState()
    object Loading : EventUiState()
    data class Error(val message: String) : EventUiState()
    data class Success(
        val event: Event,
        val favoriteSessionIds: Set<Int> = emptySet()
    ) : EventUiState()
}