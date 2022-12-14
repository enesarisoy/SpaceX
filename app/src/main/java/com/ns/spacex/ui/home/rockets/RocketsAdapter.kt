package com.ns.spacex.ui.home.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ns.spacex.R
import com.ns.spacex.databinding.ItemRocketsBinding
import com.ns.spacex.model.Rockets
import com.ns.spacex.util.downloadImage

class RocketsAdapter(
    private val favoriteClickInterface: FavoriteClickInterface
) :
    RecyclerView.Adapter<RocketsAdapter.RocketsViewHolder>() {

    inner class RocketsViewHolder(val binding: ItemRocketsBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Rockets>() {
        override fun areItemsTheSame(oldItem: Rockets, newItem: Rockets): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Rockets, newItem: Rockets): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder =
        RocketsViewHolder(
            ItemRocketsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        val rocket = differ.currentList[position]

        holder.binding.run {
            textName.text = rocket.name
            imgRocket.downloadImage(rocket.flickrImages[0])
            if (rocket.isLiked) {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.ic_star_full
                    )
                )
            } else {
                btnFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                        R.drawable.ic_star_empty
                    )
                )
            }
            btnFavorite.setOnClickListener {
                favoriteClickInterface.onClickFavorite(rocket)

            }
            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(rocket) }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Rockets) -> Unit)? = null

    fun setOnItemClickListener(listener: (Rockets) -> Unit) {
        onItemClickListener = listener
    }

    fun deleteRocket(id: String) {
        val index = differ.currentList.indexOfFirst {
            id == it.id
        }
        notifyItemRemoved(index)
    }

    fun updateRocket(rockets: Rockets) {
        val index = differ.currentList.indexOfFirst {
            rockets.id == it.id
        }
        notifyItemChanged(index)
    }


}