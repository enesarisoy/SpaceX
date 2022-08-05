package com.ns.spacex.ui.home.rockets

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
import java.lang.Exception

//TODO Difference between androidviewmodel - viewmodel, try to use viewmodel
class RocketsViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val repository: SpaceXRepository = SpaceXRepository()

    private val roomRepository: RoomRepository

    init {
        val dao = RocketDatabase.invoke(application).getRocketDao()
        roomRepository = RoomRepository(dao)
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

    fun saveRocket(rockets: Rockets) = viewModelScope.launch {
        roomRepository.upsertRocket(rockets)
    }

    fun checkFavorite(id: String) = viewModelScope.launch {
//        roomRepository.checkFavorite(id)
    }


}