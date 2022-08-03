package com.ns.spacex.ui.home.rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ns.spacex.repository.SpaceXRepository
import java.lang.IllegalArgumentException

class RocketsViewModelFactory(
    private val spaceXRepository: SpaceXRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RocketsViewModel::class.java)) {
            return RocketsViewModel(spaceXRepository) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }
}