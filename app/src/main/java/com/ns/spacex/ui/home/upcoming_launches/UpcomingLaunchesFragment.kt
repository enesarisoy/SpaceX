package com.ns.spacex.ui.home.upcoming_launches

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentUpcomingLaunchesBinding
import com.ns.spacex.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingLaunchesFragment : Fragment(R.layout.fragment_upcoming_launches) {

    private var _binding: FragmentUpcomingLaunchesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpcomingLaunchesViewModel by viewModels()
    lateinit var launchesAdapter: UpcomingLaunchesAdapter
    private val TAG = "UpcomingLaunches"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingLaunchesBinding.bind(view)

        setupRecyclerView()
        initOnClick()
        initObservers()

    }

    private fun initObservers() {
        viewModel.getUpcomingLaunches().observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { launches ->
                            launchesAdapter.differ.submitList(launches)
                        }
                    }
                    Status.ERROR -> it.message?.let { it1 -> Log.e(TAG, it1) }
                    Status.LOADING -> Log.e(TAG, "Loading")
                }
            }
        }
    }

    private fun initOnClick() {
        launchesAdapter.setOnItemClickListener {

            findNavController().navigate(
                UpcomingLaunchesFragmentDirections.actionUpcomingLaunchesFragmentToLaunchDetailFragment(
                    it
                )
            )
        }
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