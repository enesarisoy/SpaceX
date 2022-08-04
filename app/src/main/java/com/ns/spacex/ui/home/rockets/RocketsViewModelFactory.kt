package com.ns.spacex.ui.home.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.repository.SpaceXRepository
import java.lang.IllegalArgumentException

class RocketsViewModelFactory(
    private val roomRepository: RoomRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RocketsViewModel::class.java)) {
            return RocketsViewModel(roomRepository) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }
}
