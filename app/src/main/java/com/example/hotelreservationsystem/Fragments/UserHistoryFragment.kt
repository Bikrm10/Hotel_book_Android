package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservationsystem.Adapters.BookingsAdapter
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.GetAllHotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentUserHistoryBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHistoryFragment : Fragment() {

    lateinit var  binding :FragmentUserHistoryBinding
    var userId:String? = null
    private  val getAllHotelViewModel by viewModels<GetAllHotelViewModel> ()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserHistoryBinding.inflate(layoutInflater,container,false)

        userId = requireArguments().getString("userId")

        Log.d(TAG,"history fragment ma aauxa user id $userId")

        getAllHotelViewModel.userBookingd(userId!!)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllHotelViewModel.userBookingsLiveData.observe(viewLifecycleOwner, Observer {
            when(it)
            {
                is NetworkResult.Success->{

                    val responseData = it.data?.booking
                    Log.d("data from user are ","$responseData")

                    //s etiing up for recycler View
                    val Data = it.data?.booking
                    val recycler = binding.userBookRoomViewRecyclerview
                    val bookingsAdapter = BookingsAdapter(requireContext(),Data!!,getAllHotelViewModel)
                    recycler.adapter = bookingsAdapter
                    recycler.layoutManager =LinearLayoutManager(requireContext())

                }
                is NetworkResult.Loading->{

                }
                is NetworkResult.Error->
                {

                }
            }
        })
    }


}