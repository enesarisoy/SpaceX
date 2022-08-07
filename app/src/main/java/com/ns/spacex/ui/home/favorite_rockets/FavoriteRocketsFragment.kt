package com.ns.spacex.ui.home.favorite_rockets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentFavoriteRocketsBinding
import com.ns.spacex.databinding.FragmentRocketsBinding
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

        viewModel.getSavedRockets().observe(viewLifecycleOwner, Observer {

            rocketsAdapter.differ.submitList(it.toList())


        })

        rocketsAdapter.setOnItemClickListener {
            viewModel.deleteRocket(it)
        }

      /*  rocketsAdapter.setOnItemClickListener {
            findNavController().navigate(
                FavoriteRocketsFragmentDirections.actionFavoriteRocketsFragmentToRocketDetailFragment(
                    it
                )
            )
        }*/
    }


    private fun setupRecyclerView() {
        rocketsAdapter = RocketsAdapter(this)
        binding.recyclerViewRockets.apply {
            adapter = rocketsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun checkFavorite(id: String, rockets: Rockets) {
        viewModel.checkFavorite(id, rockets).observe(viewLifecycleOwner, Observer {
            if (it.data == true) {
                viewModel.deleteRocket(rockets)
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.upsert(rockets)
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickFavorite(rockets: Rockets) {
        checkFavorite(rockets.id, rockets)
    }

}