package com.ns.spacex.ui.home.rockets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentRocketsBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.util.Status


class RocketsFragment : Fragment(R.layout.fragment_rockets), FavoriClickInterface {

    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RocketsViewModel by activityViewModels()
    lateinit var rocketsAdapter: RocketsAdapter
    val TAG = "RocketsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRocketsBinding.bind(view)
        setupRecyclerView()

        rocketsAdapter.setOnItemClickListener {

            findNavController().navigate(
                RocketsFragmentDirections.actionRocketsFragmentToRocketDetailFragment(it)

            )
        }





        viewModel.getRockets().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { rockets ->
                            retrieveList(rockets)
                        }
                    }
                    Status.ERROR -> {
                        Log.e(TAG, it.message!!)
                    }
                    Status.LOADING -> Log.e(TAG, "Loading")
                }
            }
        })


    }

    private fun retrieveList(rockets: List<Rockets>) {
        rocketsAdapter.apply {
            addRockets(rockets)
            notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        rocketsAdapter = RocketsAdapter(arrayListOf(), this)
        binding.recyclerViewRockets.apply {
            adapter = rocketsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickFavorite(rockets: Rockets) {
        viewModel.saveRocket(rockets)
    }
}