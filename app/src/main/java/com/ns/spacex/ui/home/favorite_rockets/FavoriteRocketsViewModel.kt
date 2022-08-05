package com.ns.spacex.ui.home.favorite_rockets

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ns.spacex.data.RocketDatabase
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
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
        repository.deleteRocket(rockets)
    }

    fun upsert(rockets: Rockets) = viewModelScope.launch {
        repository.upsertRocket(rockets)
    }
}