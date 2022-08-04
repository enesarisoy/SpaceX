package com.ns.spacex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ns.spacex.R
import com.ns.spacex.data.RocketDatabase
import com.ns.spacex.databinding.ActivityMainBinding
import com.ns.spacex.repository.RoomRepository
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.ui.home.favorite_rockets.FavoriteRocketsViewModel
import com.ns.spacex.ui.home.favorite_rockets.FavoriteRocketsViewModelFactory
import com.ns.spacex.ui.home.launch_detail.LaunchDetailViewModel
import com.ns.spacex.ui.home.launch_detail.LaunchDetailViewModelFactory
import com.ns.spacex.ui.home.rocket_detail.RocketDetailViewModel
import com.ns.spacex.ui.home.rocket_detail.RocketDetailViewModelFactory
import com.ns.spacex.ui.home.rockets.RocketsViewModel
import com.ns.spacex.ui.home.rockets.RocketsViewModelFactory
import com.ns.spacex.ui.home.upcoming_launches.UpcomingLaunchesViewModel
import com.ns.spacex.ui.home.upcoming_launches.UpcomingLaunchesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RocketsViewModel
    lateinit var detailViewModel: RocketDetailViewModel
    lateinit var upcomingViewModel: UpcomingLaunchesViewModel
    lateinit var launchDetailViewModel: LaunchDetailViewModel
    lateinit var favoriteRocketsViewModel: FavoriteRocketsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val spaceXRepository = SpaceXRepository()
        val roomRepository = RoomRepository(RocketDatabase(this))

        val viewModelProviderFactory = RocketsViewModelFactory(roomRepository)
        val detailViewModelProviderFactory =
            RocketDetailViewModelFactory(spaceXRepository, roomRepository)
        val upcomingViewModelProviderFactory = UpcomingLaunchesViewModelFactory(spaceXRepository)
        val launchDetailViewModelFactory = LaunchDetailViewModelFactory(spaceXRepository)
        val favoriteViewModelFactory = FavoriteRocketsViewModelFactory(roomRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[RocketsViewModel::class.java]

        detailViewModel = ViewModelProvider(
            this,
            detailViewModelProviderFactory
        )[RocketDetailViewModel::class.java]

        upcomingViewModel = ViewModelProvider(
            this,
            upcomingViewModelProviderFactory
        )[UpcomingLaunchesViewModel::class.java]

        launchDetailViewModel =
            ViewModelProvider(this, launchDetailViewModelFactory)[LaunchDetailViewModel::class.java]

        favoriteRocketsViewModel =
            ViewModelProvider(this, favoriteViewModelFactory)[FavoriteRocketsViewModel::class.java]




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setupWithNavController(
            Navigation.findNavController(
                this,
                R.id.navHostFragment
            )
        )
    }
}