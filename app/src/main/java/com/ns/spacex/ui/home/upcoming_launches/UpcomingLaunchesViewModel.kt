package com.ns.spacex.ui.home.upcoming_launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.util.Resource
import kotlinx.coroutines.Dispatchers

class UpcomingLaunchesViewModel(
    private val repository: SpaceXRepository
) : ViewModel() {

    init {
        getUpcomingLaunches()
    }

    fun getUpcomingLaunches() = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getUpcomingLaunches()))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }

}