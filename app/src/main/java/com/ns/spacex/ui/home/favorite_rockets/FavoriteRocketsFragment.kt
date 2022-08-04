package com.ns.spacex.ui.home.favorite_rockets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            retrieveList(it)

            //swipe for delete
             val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                 ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                 ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
             ) {
                 override fun onMove(
                     recyclerView: RecyclerView,
                     viewHolder: RecyclerView.ViewHolder,
                     target: RecyclerView.ViewHolder
                 ): Boolean {
                     return true
                 }

                 override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                     val position = viewHolder.adapterPosition
                     val rocket = it[position]
                         viewModel.deleteRocket(rocket)
                     Snackbar.make(view, "Deleted!", Snackbar.LENGTH_LONG).apply {
                         setAction("Undo") {
                             viewModel.upsert(rocket)
                         }
                         show()
                     }
                 }
             }
            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(binding.recyclerViewRockets)
            }

        })


        rocketsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
//                putSerializable("roket", it)
                putParcelable("roket,", it)
            }
            findNavController().navigate(
                R.id.action_favoriteRocketsFragment_to_rocketDetailFragment,
                bundle
            )

        }


    }


    private fun setupRecyclerView() {
        rocketsAdapter = RocketsAdapter(arrayListOf(), this)
        binding.recyclerViewRockets.apply {
            adapter = rocketsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun retrieveList(rockets: List<Rockets>) {
        rocketsAdapter.apply {
            addRockets(rockets)
            notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickFavorite(rockets: Rockets) {
        TODO("Not yet implemented")
    }

}