package com.ns.spacex.ui.home.rocket_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RocketDetailViewModel(
    private val repository: SpaceXRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    fun getRocketDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getRocketDetail(id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

    fun saveRocket(rockets: Rockets) = viewModelScope.launch {
        roomRepository.upsertRocket(rockets)
    }

}