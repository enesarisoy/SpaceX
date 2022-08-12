package com.ns.spacex.ui.home.rocket_detail

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
import javax.inject.Inject

@HiltViewModel
class RocketDetailViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val repository: SpaceXRepository
) : ViewModel() {

    fun getRocketDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getRocketDetail(id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    fun upsert(rockets: Rockets) = viewModelScope.launch {
        rockets.isLiked = true
        roomRepository.upsertRocket(rockets)
    }

    fun deleteById(rockets: Rockets) = viewModelScope.launch {
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