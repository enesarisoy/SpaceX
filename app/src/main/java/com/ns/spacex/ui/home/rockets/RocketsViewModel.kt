package com.ns.spacex.ui.home.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val repository: SpaceXRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    init {
        getRockets()
    }

    fun getRockets() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getAllRockets()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    fun getSavedRockets() = roomRepository.getSavedRockets()

    fun upsert(rockets: Rockets) = viewModelScope.launch {
        rockets.isLiked = true
        roomRepository.upsertRocket(rockets)
    }

    fun deleteById(id: String, rockets: Rockets) = viewModelScope.launch {
        rockets.isLiked = false
        roomRepository.deleteById(rockets.id)
    }

    fun checkFavorite(id: String) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = roomRepository.checkFavorite(id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message.toString()))
        }
    }


}