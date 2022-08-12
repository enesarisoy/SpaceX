package com.ns.spacex.ui.home.rockets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentRocketsBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketsFragment : Fragment(R.layout.fragment_rockets), FavoriteClickInterface {

    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RocketsViewModel by viewModels()
    lateinit var rocketsAdapter: RocketsAdapter
    val TAG = "RocketsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRocketsBinding.bind(view)

        setupRecyclerView()
        initObservers()
        initOnClick()

    }

    private fun initObservers() {
        viewModel.getRockets().observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { response ->
                            getFavoritesData(response)
                            setProgressBar(View.GONE)
                        }
                    }
                    Status.ERROR -> it.message?.let { it1 -> Log.e(TAG, it1) }

                    Status.LOADING -> {
                        Log.e(TAG, "Loading")
                        setProgressBar(View.VISIBLE)
                    }
                }
            }
        }
    }

    private fun initOnClick() {
        rocketsAdapter.setOnItemClickListener {
            findNavController().navigate(
                RocketsFragmentDirections.actionRocketsFragmentToRocketDetailFragment(it)
            )
        }
    }

    private fun checkFavorite(rockets: Rockets) {
        viewModel.checkFavorite(rockets.id).observe(viewLifecycleOwner) {
            if (it.data == true) {
                viewModel.deleteById(rockets.id, rockets)
                rocketsAdapter.updateRocket(rockets)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.upsert(rockets)
                rocketsAdapter.updateRocket(rockets)
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        rocketsAdapter = RocketsAdapter(this)

        binding.recyclerViewRockets.apply {
            adapter = rocketsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun getFavoritesData(rockets: List<Rockets>) {
        var savedList: List<Rockets>

        viewModel.getSavedRockets().observe(viewLifecycleOwner) {
            savedList = it
            savedList.forEach { favoriteRocket ->
                rockets.find { rocket ->
                    rocket.id == favoriteRocket.id
                }?.also {
                    it.isLiked = true
                }
            }
            rocketsAdapter.differ.submitList(rockets)
        }
    }

    private fun setProgressBar(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    override fun onClickFavorite(rockets: Rockets) {
        checkFavorite(rockets)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}