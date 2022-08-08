package com.ns.spacex.ui.home.rockets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
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
                        resource.data?.let { response ->
                            rocketsAdapter.differ.submitList(response)
                            setProgressBar(View.GONE)
                            for (i in 0..response.size) {
                                viewModel.checkFavorite(response[i].id).observe(viewLifecycleOwner, Observer {
                                    if (response[i].isLiked) {
                                        Log.e(TAG, "Sonuc: " + response[i].name.toString() + response[i].isLiked.toString())
                                        onClickFavorite(response[i])
                                    } else {
                                        Log.e(TAG, "Sonuc: " + response[i].name.toString() + response[i].isLiked.toString())

                                    }
                                })


                            }
                        }
                    }
                    Status.ERROR -> {
                        Log.e(TAG, it.message!!)
                    }
                    Status.LOADING -> {
                        Log.e(TAG, "Loading")
                        setProgressBar(View.VISIBLE)
                    }
                }
            }
        })



    }

    private fun setProgressBar(visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    private fun checkFavorite(rockets: Rockets) {
        viewModel.checkFavorite(rockets.id).observe(viewLifecycleOwner, Observer {
            if (it.data == true) {
                viewModel.deleteById(rockets.id, rockets)
                rockets.isLiked = false
                Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.upsert(rockets)
                rockets.isLiked = true
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun setupRecyclerView() {
        rocketsAdapter = RocketsAdapter(this)

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
        checkFavorite(rockets)
    }
}