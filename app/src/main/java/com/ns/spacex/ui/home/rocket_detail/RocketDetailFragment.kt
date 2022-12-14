package com.ns.spacex.ui.home.rocket_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentRocketDetailBinding
import com.ns.spacex.ui.MainActivity
import com.ns.spacex.util.Status
import com.ns.spacex.util.downloadImageForCarousel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketDetailFragment : Fragment(R.layout.fragment_rocket_detail) {
    private var _binding: FragmentRocketDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RocketDetailViewModel by viewModels()
    val args: RocketDetailFragmentArgs by navArgs()
    val TAG = "RocketDetailFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRocketDetailBinding.bind(view)

        initObservers()
        initOnClick()
        backButton()
        setDrawable()
    }

    private fun initOnClick() {
        binding.btnFavorite.setOnClickListener {
            viewModel.checkFavorite(args.roket.id).observe(viewLifecycleOwner) {
                if (it.data == true) {
                    viewModel.deleteById(args.roket)
                    binding.btnFavorite.setImageDrawable(
                        view?.let { it1 ->
                            ContextCompat.getDrawable(
                                it1.context,
                                R.drawable.ic_star_empty
                            )
                        }
                    )
                    view?.let { it1 -> Snackbar.make(it1, "Rocket deleted!", Snackbar.LENGTH_LONG).show() }
                } else {
                    viewModel.upsert(args.roket)
                    binding.btnFavorite.setImageDrawable(
                        view?.let { it1 ->
                            ContextCompat.getDrawable(
                                it1.context,
                                R.drawable.ic_star_full
                            )
                        }
                    )
                    view?.let { it1 -> Snackbar.make(it1, "Rocket saved!", Snackbar.LENGTH_LONG).show() }
                }

            }
        }

    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }



    private fun initObservers() {
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
    }

    private fun setDrawable() {
        if (args.roket.isLiked) {
            binding.btnFavorite.setImageDrawable(
                view?.let {
                    ContextCompat.getDrawable(
                        it.context,
                        R.drawable.ic_star_full
                    )
                }
            )
        } else {
            binding.btnFavorite.setImageDrawable(
                view?.let {
                    ContextCompat.getDrawable(
                        it.context,
                        R.drawable.ic_star_empty
                    )
                }
            )
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