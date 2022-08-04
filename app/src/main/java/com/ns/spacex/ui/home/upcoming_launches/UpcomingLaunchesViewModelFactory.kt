package com.ns.spacex.ui.home.upcoming_launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ns.spacex.repository.SpaceXRepository
import java.lang.IllegalArgumentException

class UpcomingLaunchesViewModelFactory(
    private val spaceXRepository: SpaceXRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingLaunchesViewModel::class.java)) {
            return UpcomingLaunchesViewModel(spaceXRepository) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }
}