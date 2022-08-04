package com.ns.spacex.ui.home.launch_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.util.Resource
import kotlinx.coroutines.Dispatchers

class LaunchDetailViewModel(
    private val repository: SpaceXRepository
): ViewModel() {
    fun getLaunchDetail(id: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.getLaunchDetail(id)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error"))
        }
    }
}