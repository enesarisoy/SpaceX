package com.ns.spacex.ui.home.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.util.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class RocketsViewModel(
    private val repository: SpaceXRepository
) : ViewModel() {


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


}