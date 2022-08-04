package com.ns.spacex.ui.home.launch_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ns.spacex.repository.SpaceXRepository
import java.lang.IllegalArgumentException

class LaunchDetailViewModelFactory(
    private val spaceXRepository: SpaceXRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LaunchDetailViewModel::class.java)) {
            return LaunchDetailViewModel(spaceXRepository) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }
}