package com.apni.beyond.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apni.beyond.data.model.ApodResponse
import com.apni.beyond.data.repository.SavedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SavedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SavedRepository(application)

    private val _savedPhotos = MutableStateFlow(repository.getSavedPhotos())
    val savedPhotos: StateFlow<List<ApodResponse>> = _savedPhotos.asStateFlow()

    private val _savedIds = MutableStateFlow(repository.getSavedIds())
    val savedIds: StateFlow<Set<String>> = _savedIds.asStateFlow()

    fun isSaved(id: String): Boolean {
        return _savedIds.value.contains(id)
    }

    fun toggleSaved(item: ApodResponse) {
        repository.toggleSaved(item)
        refreshSaved()
    }

    fun removeSaved(id: String) {
        repository.removeSaved(id)
        refreshSaved()
    }

    private fun refreshSaved() {
        _savedPhotos.value = repository.getSavedPhotos()
        _savedIds.value = repository.getSavedIds()
    }
}
