package com.ns.spacex.ui.home.favorite_rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
import kotlinx.coroutines.launch

class FavoriteRocketsViewModel(
    private val repository: RoomRepository
) : ViewModel() {

    fun getSavedRockets() = repository.getSavedRockets()
    fun deleteRocket(rockets: Rockets) = viewModelScope.launch {
        repository.deleteRocket(rockets)
    }

    fun upsert(rockets: Rockets) = viewModelScope.launch {
        repository.upsertRocket(rockets)
    }
}