package com.ns.spacex.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.ns.spacex.R
import com.ns.spacex.databinding.ActivityMainBinding
import com.ns.spacex.ui.home.favorite_rockets.FavoriteRocketsViewModel
import com.ns.spacex.ui.home.rocket_detail.RocketDetailViewModel
import com.ns.spacex.ui.home.rockets.RocketsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var rocketViewModel: RocketsViewModel
    lateinit var rocketDetailViewModel: RocketDetailViewModel
    lateinit var favoriteRocketsViewModel: FavoriteRocketsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        rocketViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RocketsViewModel::class.java]

        rocketDetailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[RocketDetailViewModel::class.java]

        favoriteRocketsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[FavoriteRocketsViewModel::class.java]



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