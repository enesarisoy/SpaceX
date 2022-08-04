package com.ns.spacex.ui.home.rockets

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ns.spacex.databinding.ItemRocketsBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.util.downloadImage

class RocketsAdapter(private val rockets: ArrayList<Rockets>, private val favoriClick: FavoriClickInterface) :
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
            btnFavorite.setOnClickListener {
                favoriClick.onClickFavorite(rockets)
                Log.e("denem","asdsad")
            }
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