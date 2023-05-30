package com.example.hotelreservationsystem.Fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hotelreservationsystem.Adapters.RecomenderAdapter
import com.example.hotelreservationsystem.Adapters.ReviewsAdapterTest
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.TestModels.DataModel
import com.example.hotelreservationsystem.TestModels.ReviewModel
import com.example.hotelreservationsystem.databinding.FragmentOnTouchUserBinding
import com.example.hotelreservationsystem.utils.constants.TAG
import me.ibrahimsn.lib.SmoothBottomBar


class OnTouchUserFragment : Fragment() {
    lateinit var binding:com.example.hotelreservationsystem.databinding.FragmentOnTouchUserBinding
    private val args by navArgs<OnTouchUserFragmentArgs>()


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnTouchUserBinding.inflate(layoutInflater,container,false);




        // setting the hotel name and description with the hotel name from safe argument comming on click action
        binding.hotelName.text = args.hotel.name
        binding.hotelDescription.text = args.hotel.description


        // image for sliding view



        val imageList = ArrayList<SlideModel>() // Create image list for sliding purpose

        imageList.add(SlideModel(R.drawable.tst,scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.tst1,scaleType = ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.tst2,scaleType = ScaleTypes.FIT))
        binding. imageSlider.setImageList(imageList, ScaleTypes.FIT)
        binding.imageSlider.setImageList(imageList)

        binding.availableRooms.setOnClickListener{
            val hotel = args.hotel
            val userId = args.userId
            val action = OnTouchUserFragmentDirections.actionOnTouchUserFragmentToViewRoomFragment(hotel,userId)

            findNavController().navigate(action)

        }

        val manualData = ArrayList<DataModel>()
        manualData.add(DataModel("Trojan National Hotel","United",1))
        manualData.add(DataModel("Trojan National Hotel","United",1))
        manualData.add(DataModel("sigma National Hotel","United",1))
        manualData.add(DataModel("LojanNational Hotel","United",1))
        manualData.add(DataModel("TarhanNational Hotel","United",1))
        manualData.add(DataModel("Trojan National Hotel","United",1))
        manualData.add(DataModel("famndfk fkanlkd Hotel","United",1))
        manualData.add(DataModel("Trojan National Hotel","United",1))
        manualData.add(DataModel("Sirha National Hotel","United",1))
        manualData.add(DataModel("Trojan National Hotel","United",1))
        val recommendedAdapter = RecomenderAdapter(requireContext(),manualData)
        // if i want to set the recycler view for recommendation system
        val recomenderrecycleView = binding.recomenderRecyclerView
        recomenderrecycleView.adapter = recommendedAdapter
        recomenderrecycleView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)


        // setiing for review recycler view


       val ManualReviewData = ArrayList<ReviewModel>()
        ManualReviewData.add(ReviewModel("BIkash Hujdar","2076/12/3",R.drawable.profiletest,"They were extremely accommodating and allowed us to check in early at like 10am. We got to hotel super early and I didn’t wanna wait. So this was a big plus. The sevice was exceptional as well. Would definitely send a friend there.",4))
        ManualReviewData.add(ReviewModel("BIkash Hujdar","2076/12/3",R.drawable.profiletest,"This is the Best Hotel in the asia",4))
        ManualReviewData.add(ReviewModel("BIkash Hujdar","2076/12/3",R.drawable.profiletest,"TThey were extremely accommodating and allowed us to check in early at like 10am. We got to hotel super early and I didn’t wanna wait. So this was a big plus. The sevice was exceptional as well. Would definitely send a friend there.",4))
        ManualReviewData.add(ReviewModel("BIkash Hujdar","2076/12/3",R.drawable.profiletest,"This is the Best Hotel in the asia",4))
        ManualReviewData.add(ReviewModel("BIkash Hujdar","2076/12/3",R.drawable.profiletest,"This is the Best Hotel in the asia",4))
        ManualReviewData.add(ReviewModel("BIkash Hujdar","2076/12/3",R.drawable.profiletest,"This is the Best Hotel in the asia",4))
        ManualReviewData.add(ReviewModel("BIkash Hujdar","2076/12/3",R.drawable.profiletest,"This is the Best Hotel in the asia",4))
         val reviewAdapter =ReviewsAdapterTest(requireContext(),ManualReviewData)
        val reviewRecycleview = binding.reviewRecyclerView
        reviewRecycleview.adapter = reviewAdapter
        reviewRecycleview.layoutManager = LinearLayoutManager(requireContext());
        return binding.root



    }

}