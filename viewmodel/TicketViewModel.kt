package com.mohsin.eventcompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mohsin.eventcompanion.data.api.RetrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TicketViewModel : ViewModel() {

    private val _qrCodeData = MutableStateFlow<String>("")
    val qrCodeData: StateFlow<String> = _qrCodeData

    fun refreshTicket() {
        viewModelScope.launch {
            try {
                val ticket = RetrofitService.api.getTicket()
                _qrCodeData.value = ticket.qrCodeData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

class TicketViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            return TicketViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
