package com.apni.beyond.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apni.beyond.data.repository.SavedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SavedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SavedRepository(application)

    private val _savedIds = MutableStateFlow(repository.getSavedIds())
    val savedIds: StateFlow<Set<String>> = _savedIds.asStateFlow()

    fun isSaved(id: String): Boolean {
        return _savedIds.value.contains(id)
    }

    fun toggleSaved(id: String) {
        repository.toggleSaved(id)
        _savedIds.value = repository.getSavedIds()
    }
}
