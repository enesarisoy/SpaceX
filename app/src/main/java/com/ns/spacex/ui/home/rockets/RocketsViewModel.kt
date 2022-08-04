package com.ns.spacex.ui.home.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.ns.spacex.model.Rockets
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RocketsViewModel(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val repository: SpaceXRepository = SpaceXRepository()



    init {
        getRockets()
    }

   fun getRockets() = liveData(Dispatchers.IO){
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


}