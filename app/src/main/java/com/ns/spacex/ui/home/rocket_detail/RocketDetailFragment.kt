package com.ns.spacex.ui.home.rocket_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentRocketDetailBinding
import com.ns.spacex.ui.MainActivity
import com.ns.spacex.util.Status
import com.ns.spacex.util.downloadImageForCarousel


class RocketDetailFragment : Fragment(R.layout.fragment_rocket_detail) {
    private var _binding: FragmentRocketDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RocketDetailViewModel by activityViewModels()
    val args: RocketDetailFragmentArgs by navArgs()
    val TAG = "RocketDetailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentRocketDetailBinding.bind(view)

        args.roket.let {
            viewModel.getRocketDetail(it.id).observe(viewLifecycleOwner) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let {
                                binding.apply {
                                    textName.text = it.name ?: "deneme"
                                    textDesciption.text = it.description
                                    initCarousel(it.flickrImages)
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


        binding.btnFavorite.setOnClickListener {
            viewModel.checkFavorite(args.roket.id).observe(viewLifecycleOwner) {
                if (it.data == true) {
                    viewModel.deleteById(args.roket)
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.upsert(args.roket)
                    binding.btnFavorite.setColorFilter(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.black,
                            null
                        )
                    )
                    Snackbar.make(view, "Rocket saved!", Snackbar.LENGTH_LONG).show()
                }

            }
        }

        binding.btnBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

    }

    private fun initCarousel(images: List<String>) {
        binding.carouselView.apply {

            size = images.size
            autoPlay = true
            resource = R.layout.carousel_home_item
            indicatorAnimationType = IndicatorAnimationType.SCALE
            carouselOffset = OffsetType.CENTER
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.imgCarousel)
                var imgPath: String? = images[position]
                if (imgPath.isNullOrEmpty())
                    imgPath = ""
                imageView.downloadImageForCarousel(imgPath)
            }
            show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}