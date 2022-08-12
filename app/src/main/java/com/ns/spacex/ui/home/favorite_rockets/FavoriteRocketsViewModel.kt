package com.ns.spacex.ui.home.favorite_rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRocketsViewModel @Inject constructor(
    private val repository: RoomRepository,
) : ViewModel() {

    fun getSavedRockets() = repository.getSavedRockets()
    fun deleteRocket(rockets: Rockets) = viewModelScope.launch {
        rockets.isLiked = false
        repository.deleteRocket(rockets)
    }

    fun upsert(rockets: Rockets) = viewModelScope.launch {
        rockets.isLiked = true
        repository.upsertRocket(rockets)
    }

    fun checkFavorite(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repository.checkFavorite(id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message.toString()))
        }
    }
}