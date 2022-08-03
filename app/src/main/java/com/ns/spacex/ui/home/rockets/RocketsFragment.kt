package com.ns.spacex.ui.home.rockets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentRocketsBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.util.Resource
import com.ns.spacex.util.Status
import kotlinx.parcelize.parcelableCreator


class RocketsFragment : Fragment(R.layout.fragment_rockets) {

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
            val bundle = Bundle().apply {
                //TODO("Difference between parcelable - serializable")
//                putParcelable("roket,", it)
                putSerializable("roket",it)
            }
            findNavController().navigate(
                R.id.action_rocketsFragment_to_rocketDetailFragment,
                bundle
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
        rocketsAdapter = RocketsAdapter(arrayListOf())
        binding.recyclerViewRockets.apply {
            adapter = rocketsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}