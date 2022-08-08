package com.ns.spacex.ui.home.upcoming_launches

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentUpcomingLaunchesBinding
import com.ns.spacex.model.upcoming_launches.UpcomingLaunchesModel
import com.ns.spacex.ui.home.rockets.RocketsFragmentDirections
import com.ns.spacex.util.Status


class UpcomingLaunchesFragment : Fragment(R.layout.fragment_upcoming_launches) {

    private var _binding: FragmentUpcomingLaunchesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpcomingLaunchesViewModel by activityViewModels()
    lateinit var launchesAdapter: UpcomingLaunchesAdapter
    private val TAG = "UpcomingLaunches"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingLaunchesBinding.bind(view)
        setupRecyclerView()

        launchesAdapter.setOnItemClickListener {

            findNavController().navigate(
                UpcomingLaunchesFragmentDirections.actionUpcomingLaunchesFragmentToLaunchDetailFragment(it)
            )
        }


        viewModel.getUpcomingLaunches().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { launches ->
                            launchesAdapter.differ.submitList(launches)
                        }
                    }
                    Status.ERROR -> Log.e(TAG, it.message!!)
                    Status.LOADING -> Log.e(TAG, "Loading")
                }
            }
        })
    }


    private fun setupRecyclerView() {
        launchesAdapter = UpcomingLaunchesAdapter()
        binding.recyclerViewLaunches.apply {
            adapter = launchesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}