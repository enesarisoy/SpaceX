package com.ns.spacex.ui.home.upcoming_launches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentUpcomingLaunchesBinding


class UpcomingLaunchesFragment : Fragment(R.layout.fragment_upcoming_launches) {

    private var _binding: FragmentUpcomingLaunchesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpcomingLaunchesBinding.bind(view)
    }

   /* override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_launches, container, false)
    }*/


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}