package com.ns.spacex.ui.home.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.spacex.databinding.ItemRocketsBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.util.downloadImage

class RocketsAdapter(private val rockets: ArrayList<Rockets>) :
    RecyclerView.Adapter<RocketsAdapter.RocketsViewHolder>() {
    inner class RocketsViewHolder(val binding: ItemRocketsBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder =
        RocketsViewHolder(
            ItemRocketsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        val rockets = rockets[position]

        holder.binding.apply {
            textName.text = rockets.name
            imgRocket.downloadImage(rockets.flickrImages[0])
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(rockets) }
            }
        }
    }

    override fun getItemCount(): Int = rockets.size

    fun addRockets(rockets: List<Rockets>) {
        this.rockets.apply {
            clear()
            addAll(rockets)
        }
    }

    private var onItemClickListener: ((Rockets) -> Unit)? = null

    fun setOnItemClickListener(listener: (Rockets) -> Unit) {
        onItemClickListener = listener
    }
}