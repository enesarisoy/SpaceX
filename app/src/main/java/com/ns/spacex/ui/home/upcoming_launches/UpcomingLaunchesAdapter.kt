package com.ns.spacex.ui.home.upcoming_launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ns.spacex.databinding.ItemUpcomingLaunchesBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.model.upcoming_launches.UpcomingLaunchesModel

class UpcomingLaunchesAdapter(private val launches: ArrayList<UpcomingLaunchesModel>) :
    RecyclerView.Adapter<UpcomingLaunchesAdapter.LaunchesViewHolder>() {
    inner class LaunchesViewHolder(val binding: ItemUpcomingLaunchesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder =
        LaunchesViewHolder(
            ItemUpcomingLaunchesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val launches = launches[position]

        holder.binding.apply {
            textName.text = launches.name
            textDate.text = launches.date
            textFlightNumber.text = launches.flightNumber.toString()
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(launches) }
            }
        }
    }

    override fun getItemCount(): Int = launches.size

    fun addLaunches(launches: List<UpcomingLaunchesModel>) {
        this.launches.apply {
            clear()
            addAll(launches)
        }
    }

    private var onItemClickListener: ((UpcomingLaunchesModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (UpcomingLaunchesModel) -> Unit) {
        onItemClickListener = listener
    }
}