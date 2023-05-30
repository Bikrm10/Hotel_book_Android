package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservationsystem.Adapters.BookingRoomAdapter
import com.example.hotelreservationsystem.Adapters.BookingsAdapter
import com.example.hotelreservationsystem.Models.BookingX
import com.example.hotelreservationsystem.Models.RoomX
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnersBookingBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._com_example_hotelreservationsystem_ViewModels_AuthViewModel_HiltModules_KeyModule
import java.lang.Exception
import kotlin.math.log

@AndroidEntryPoint
class OwnersBookingFragment : Fragment() {
    lateinit var binding : FragmentOwnersBookingBinding
    var ownerId :String? = null

val hotelViewModel by viewModels<HotelViewModel> ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentOwnersBookingBinding.inflate(layoutInflater,container,false)

       ownerId = requireArguments().getString("ownerId")
        Log.d("argument aayo","$ownerId")
        try {
            hotelViewModel.showBooking(ownerId!!)
            Log.d(TAG,"Response Generated")

        }catch (
            e:Exception
        )
        {
            Log.d(TAG,"eroor on calling to get function ${e.message}")
        }
      //  hotelViewModel.showBooking(ownerId!!)
//        Log.d("apiResponse","Ayoo")


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hotelViewModel.allbookingLiveData.observe(viewLifecycleOwner, Observer {

            when(it){

                is NetworkResult.Success ->{
                    Log.d("data from user are "," sucess ma xiro")

                    val responseData = it.data?.booking
                    Log.d("data from user are ","$responseData")
                    //s etiing up for recycler View
                    val Data = it.data?.booking
                    val recycler = binding.booikingRecycler
                    val bookingsAdapter = BookingRoomAdapter(requireContext(),Data!!)
                    recycler.adapter = bookingsAdapter
                    recycler.layoutManager =LinearLayoutManager(requireContext())

                }
                is NetworkResult.Loading ->{}
                is NetworkResult.Error ->{}
                else -> {}
            }

        })
    }


}