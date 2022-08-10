package com.ns.spacex.ui.home.upcoming_launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.spacex.databinding.ItemUpcomingLaunchesBinding
import com.ns.spacex.model.upcoming_launches.UpcomingLaunchesModel

class UpcomingLaunchesAdapter :
    RecyclerView.Adapter<UpcomingLaunchesAdapter.LaunchesViewHolder>() {
    inner class LaunchesViewHolder(val binding: ItemUpcomingLaunchesBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<UpcomingLaunchesModel>() {
        override fun areItemsTheSame(
            oldItem: UpcomingLaunchesModel,
            newItem: UpcomingLaunchesModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UpcomingLaunchesModel,
            newItem: UpcomingLaunchesModel
        ): Boolean {
            return oldItem == newItem
        }
    }


    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchesViewHolder =
        LaunchesViewHolder(
            ItemUpcomingLaunchesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LaunchesViewHolder, position: Int) {
        val launches = differ.currentList[position]

        holder.binding.apply {
            textName.text = launches.name
            textDate.text = launches.date
            textFlightNumber.text = launches.flightNumber.toString()
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(launches) }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


    private var onItemClickListener: ((UpcomingLaunchesModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (UpcomingLaunchesModel) -> Unit) {
        onItemClickListener = listener
    }
}