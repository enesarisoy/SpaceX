package com.ns.spacex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ns.spacex.R
import com.ns.spacex.databinding.ActivityMainBinding
import com.ns.spacex.repository.SpaceXRepository
import com.ns.spacex.ui.home.rocket_detail.RocketDetailViewModel
import com.ns.spacex.ui.home.rocket_detail.RocketDetailViewModelFactory
import com.ns.spacex.ui.home.rockets.RocketsViewModel
import com.ns.spacex.ui.home.rockets.RocketsViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RocketsViewModel
    lateinit var detailViewModel: RocketDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val spaceXRepository = SpaceXRepository()
        val viewModelProviderFactory = RocketsViewModelFactory(spaceXRepository)
        val detailViewModelProviderFactory = RocketDetailViewModelFactory(spaceXRepository)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)[RocketsViewModel::class.java]
        detailViewModel = ViewModelProvider(this, detailViewModelProviderFactory)[RocketDetailViewModel::class.java]







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