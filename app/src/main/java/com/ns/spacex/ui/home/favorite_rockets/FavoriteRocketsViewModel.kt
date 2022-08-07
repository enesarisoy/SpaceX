package com.ns.spacex.ui.home.favorite_rockets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ns.spacex.data.RocketDatabase
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteRocketsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository: RoomRepository

    init {
        val dao = RocketDatabase.invoke(application).getRocketDao()
        repository = RoomRepository(dao)
    }

    fun getSavedRockets() = repository.getSavedRockets()
    fun deleteRocket(rockets: Rockets) = viewModelScope.launch {
        rockets.isLiked = false
        repository.deleteRocket(rockets)
    }

    fun upsert(rockets: Rockets) = viewModelScope.launch {
        rockets.isLiked = true
        repository.upsertRocket(rockets)
    }

    fun checkFavorite(id: String, rockets: Rockets) = liveData(Dispatchers.IO) {
        try {
            emit(Resource.success(data = repository.checkFavorite(id, rockets)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message.toString()))
        }
    }
}