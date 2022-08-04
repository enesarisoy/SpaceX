package com.ns.spacex.ui.home.favorite_rockets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ns.spacex.repository.RoomRepository
import java.lang.IllegalArgumentException

class FavoriteRocketsViewModelFactory(
    private val roomRepository: RoomRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteRocketsViewModel::class.java)) {
            return FavoriteRocketsViewModel(roomRepository) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }
}