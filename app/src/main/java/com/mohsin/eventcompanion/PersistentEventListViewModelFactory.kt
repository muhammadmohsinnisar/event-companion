package com.mohsin.eventcompanion

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mohsin.eventcompanion.ui.list.PersistentEventListViewModel

class PersistentEventListViewModelFactory(
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersistentEventListViewModel::class.java)) {
            return PersistentEventListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
