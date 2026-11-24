package com.apni.beyond.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apni.beyond.data.model.NasaItem
import com.apni.beyond.data.repository.NasaImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val repository: NasaImageRepository = NasaImageRepository()
) : ViewModel() {

    private val _objects = MutableStateFlow<List<NasaItem>>(emptyList())
    val objects: StateFlow<List<NasaItem>> = _objects.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun searchObjects(query: String) {
        if (query.isBlank()) {
            _objects.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.searchObjects(query)
                .onSuccess { result ->
                    _objects.value = result
                }
                .onFailure { error ->
                    _errorMessage.value =
                        error.message ?: "Failed to load space objects."
                }

            _isLoading.value = false
        }
    }
}