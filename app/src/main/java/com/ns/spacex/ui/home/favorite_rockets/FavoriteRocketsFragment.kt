package com.ns.spacex.ui.home.favorite_rockets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentFavoriteRocketsBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.ui.home.rockets.FavoriClickInterface
import com.ns.spacex.ui.home.rockets.RocketsAdapter


class FavoriteRocketsFragment : Fragment(R.layout.fragment_favorite_rockets), FavoriClickInterface {
    private var _binding: FragmentFavoriteRocketsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteRocketsViewModel by activityViewModels()
    lateinit var rocketsAdapter: RocketsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoriteRocketsBinding.bind(view)

        setupRecyclerView()

        viewModel.getSavedRockets().observe(viewLifecycleOwner) {

            rocketsAdapter.differ.submitList(it.toList())
        }

        rocketsAdapter.setOnItemClickListener {
            findNavController().navigate(
                FavoriteRocketsFragmentDirections.actionFavoriteRocketsFragmentToRocketDetailFragment(
                    it
                )
            )
        }
    }


    private fun setupRecyclerView() {
        rocketsAdapter = RocketsAdapter(this)
        binding.recyclerViewRockets.apply {
            adapter = rocketsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun checkFavorite(rockets: Rockets) {
        viewModel.checkFavorite(rockets.id).observe(viewLifecycleOwner) {
            if (it.data == true) {
                viewModel.deleteRocket(rockets)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.upsert(rockets)
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickFavorite(rockets: Rockets) {
        checkFavorite(rockets)
    }

}