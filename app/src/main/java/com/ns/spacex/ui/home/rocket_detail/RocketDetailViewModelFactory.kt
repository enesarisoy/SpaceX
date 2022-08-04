package com.ns.spacex.ui.home.rocket_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.ui.home.rockets.RocketsViewModel
import java.lang.IllegalArgumentException

class RocketDetailViewModelFactory(
    private val spaceXRepository: SpaceXRepository,
    private val roomRepository: RoomRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RocketDetailViewModel::class.java)) {
            return RocketDetailViewModel(spaceXRepository, roomRepository) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }
}