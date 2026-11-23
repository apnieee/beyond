package com.apni.beyond.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apni.beyond.data.model.ApodResponse
import com.apni.beyond.data.repository.ApodRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ApodViewModel(
    private val repository: ApodRepository = ApodRepository()
) : ViewModel() {

    private val _apod = MutableStateFlow<ApodResponse?>(null)
    val apod: StateFlow<ApodResponse?> = _apod.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        fetchApod()
    }

    fun fetchApod() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                repository.getApod()
                    .onSuccess { response ->
                        _apod.value = response
                    }
                    .onFailure { error ->
                        _errorMessage.value = "${error::class.simpleName}: ${error.message}"
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
