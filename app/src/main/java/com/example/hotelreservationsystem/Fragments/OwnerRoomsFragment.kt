package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservationsystem.Adapters.RoomsAdapter
import com.example.hotelreservationsystem.Models.Room
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerRoomsBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class OwnerRoomsFragment : Fragment() {

    lateinit var binding: FragmentOwnerRoomsBinding
    var ownerId: String? = null
    var hoteid: String? = null



//    private  val args by navArgs<OwnerRoomsFragmentArgs>()


    private val hotelViewModel by viewModels<HotelViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerRoomsBinding.inflate(layoutInflater, container, false)

        // getting the owner id  and hotel id
        ownerId = requireArguments().getString("ownerId").toString()
        hoteid = requireArguments().getString("hotelId").toString()

        Log.d(TAG, "ownerid and hotelId  $ownerId $hoteid")

        // data should be
        //  646e22b095405e6d962cc2cb  and 646e25f9b2a982f41b6e6519

        hotelViewModel.getAllRooms(ownerId!!, hoteid!!)
            hotelViewModel.hotelLiveData.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NetworkResult.Loading -> {

                    }

                    is NetworkResult.Success -> {


                        Log.d(TAG, "  data is generated")
                        val roomData: List<Room> = it.data?.hotel!!.rooms

                        Log.d(TAG, " Room Lisy ois $roomData")
                        try {
                            val recyclerView = binding.roomViewRecyclerView
                            val roomAdapter = RoomsAdapter(requireContext(),it.data.hotel.rooms)
                            recyclerView.adapter = roomAdapter
                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
                            roomAdapter.notifyDataSetChanged()
                            // handling on item touch
                            roomAdapter.setOnItemClickListner(
                                object :RoomsAdapter.onItemClickListner
                                {
                                    override fun onItemClick(position: Int) {
                                        val roomDetails = it.data.hotel.rooms.get(position)
                                        Log.d(TAG," room id is $roomDetails")
                                        val action = OwnerRoomsFragmentDirections.actionOwnerRoomsFragmentToUpdateRoomFragment(roomDetails)
                                        findNavController().navigate(action)
                                    }

                                }
                            )


                        }
                        catch (e: Exception) {
                            Log.d(TAG, " eroor on adapting recyclerview  ${e.message}")
                        }


                    }

                    is NetworkResult.Error -> {

                    }


            }
        })



        return binding.root
    }
}
