package com.ns.spacex.ui.home.rocket_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ns.spacex.data.RocketDatabase
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RocketDetailViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository: SpaceXRepository = SpaceXRepository()
    private val roomRepository: RoomRepository

    init {
        val dao = RocketDatabase.invoke(application).getRocketDao()
        roomRepository = RoomRepository(dao)
    }

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