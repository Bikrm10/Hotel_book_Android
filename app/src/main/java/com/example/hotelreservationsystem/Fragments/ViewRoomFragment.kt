package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservationsystem.Adapters.AvailableRoomsAdapter
import com.example.hotelreservationsystem.Adapters.RoomsAdapter
import com.example.hotelreservationsystem.Models.Room
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.databinding.FragmentViewRoomBinding
import com.example.hotelreservationsystem.utils.constants.TAG
import java.lang.Exception

class ViewRoomFragment : Fragment() {
    lateinit var binding : FragmentViewRoomBinding
 private  val args by navArgs<ViewRoomFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val rooms= args.hotel.rooms
        val userId = args.userId

        Log.d(TAG," Room datas are $rooms")
        // Inflate the layout for this fragment
        binding = FragmentViewRoomBinding.inflate(layoutInflater,container, false)
        //reclerView item setting section

        val recyclerView = binding.userViewRoomsRecyclerView
        val availableRoomsAdapter = AvailableRoomsAdapter(requireContext(),rooms)
        recyclerView.adapter =availableRoomsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //availableRoomsAdapter.setOnItemClickListner(
        //            object :AvailableRoomsAdapter.onItemClickListner
        //            {
        //                override fun onItemClick(position: Int) {
        //                    try {
        //                        val roomDetails = args.hotel.rooms.get(position)
        //                        val userId = args.userId
        //                        Log.d(TAG,"room one data in  view Room are $roomDetails")
        //                        Log.d(TAG,"user ID data in  view Room are $userId")
        //                        Toast.makeText(requireContext(), "${roomDetails._id}", Toast.LENGTH_SHORT).show()
        //                        val action = ViewRoomFragmentDirections.actionViewRoomFragmentToUserBookingFragment(roomDetails,userId)
        //                        findNavController().navigate(action)
        //
        //                    }catch (e:Exception)
        //                    {
        //                        Log.d(TAG,"error  on adapter click ")
        //                    }
        //
        //
        //                }
        //
        //            }
        //        )

availableRoomsAdapter.setOnItemClickListner(object :AvailableRoomsAdapter.onItemClickListner {
    override fun onItemClick(position: Int) {


        try {
            val RoomDetails = args.hotel.rooms.get(position)

            if(RoomDetails.status.toString() == "true")
            {
                val userId = args.userId
                Log.d(TAG,"room euta in view room fragment is $RoomDetails")
                Log.d(TAG,"user id in view room fragment is $userId")
                Toast.makeText(requireContext(), "${RoomDetails._id}", Toast.LENGTH_SHORT).show()
                val action = ViewRoomFragmentDirections.actionViewRoomFragmentToUserBookingFragment(RoomDetails,userId)
                findNavController().navigate(action)
            }
            else
            {
                Toast.makeText(requireContext(), "Room is already Booked", Toast.LENGTH_SHORT).show()
            }

        }catch (e:Exception)
        {
            Log.d(TAG,"error  on adapter click ")
        }

    }

})

        return binding.root

    }
}