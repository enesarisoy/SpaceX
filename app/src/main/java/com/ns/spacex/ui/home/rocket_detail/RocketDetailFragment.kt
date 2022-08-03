package com.ns.spacex.ui.home.rocket_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.Person.fromBundle
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.ns.spacex.R
import com.ns.spacex.databinding.FragmentRocketDetailBinding
import com.ns.spacex.model.Rockets
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

        viewModel.getRocketDetail(args.roket!!.id).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let {
                            binding.apply {
                                textName.text = it.name
                                textDesciption.text = it.description
                                initCarousel(it.flickrImages)
                            }
                        }
                    }
                    Status.ERROR -> {
                        Log.e(TAG, it.message!!)
                    }
                    Status.LOADING -> Log.e(TAG, "Loading")
                }
            }
        })
       /* val roket = args.roket
        binding.apply {
            textName.text = roket?.name
            textDesciption.text = roket?.description
            initCarousel(roket!!.flickrImages)
        }
*/
        binding.btnBack.setOnClickListener{
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