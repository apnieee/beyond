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
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getApod()
                .onSuccess { response ->
                    _apod.value = response
                }
                .onFailure { error ->
                    _errorMessage.value =
                        error.message ?: "Failed to load astronomy data."
                }

            _isLoading.value = false
        }
    }
}
