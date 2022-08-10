package com.ns.spacex.ui.home.launch_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentLaunchDetailBinding
import com.ns.spacex.ui.MainActivity
import com.ns.spacex.util.Status
import com.ns.spacex.util.downloadImage


class LaunchDetailFragment : Fragment(R.layout.fragment_launch_detail) {

    private var _binding: FragmentLaunchDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LaunchDetailViewModel by activityViewModels()
    private val TAG = "LaunchDetailFragment"
    private val args: LaunchDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLaunchDetailBinding.bind(view)

        args.upcoming.id?.let {
            viewModel.getLaunchDetail(it).observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let {
                                binding.apply {
                                    textName.text = it.name
                                    textDate.text = it.date
                                    textFlightNumber.text = it.flightNumber.toString()

                                    it.links.patch.small?.let { url ->
                                        imgLaunch.visibility = View.VISIBLE
                                        imgLaunch.downloadImage(it.links.patch.small.toString())
                                    }
                                }
                            }
                        }
                        Status.ERROR -> {
                            it.message?.let { it1 -> Log.e(TAG, it1) }
                        }
                        Status.LOADING -> Log.e(TAG, "Loading")
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}