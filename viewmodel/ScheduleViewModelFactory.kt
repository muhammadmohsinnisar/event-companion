package com.mohsin.eventcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohsin.eventcompanion.data.repository.EventRepository

class ScheduleViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            return ScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
