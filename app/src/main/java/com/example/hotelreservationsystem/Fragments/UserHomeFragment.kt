package com.example.hotelreservationsystem.Fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservationsystem.Adapters.TestAdapters
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.R

import com.example.hotelreservationsystem.ViewModels.GetAllHotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentUserHomeBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class UserHomeFragment : Fragment() {
    lateinit var binding:FragmentUserHomeBinding

    private val getAllHotelViewModel by viewModels<GetAllHotelViewModel>()
    private  var ownerResponse: HotelResponse?= null
    var userId :String? = null
    //for the serialized data to handle in this fragment

    private val args by navArgs<UserHomeFragmentArgs>()

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    override  fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserHomeBinding.inflate(layoutInflater,container,false)
        //setting username in the profile head
//        binding.userName.text = args.user.user.username
        binding.userName.text = args.user.user.username

        //getting userId from the UserResponse
        userId = args.user.user._id.toString()


        // yaha dekhi tal matra acess gar hain data
        Log.d(TAG,"user Home Fragment Thiche maile and i get user ID $userId")

        getAllHotelViewModel.getAllHotel(userId!!)

        binding.shapeableImageView2.setOnClickListener{

          findNavController().navigate(R.id.action_userHomeFragment_to_userProfileFragment,Bundle().apply {
              putString("userId",userId)
          })

        }




//        // handling search view
//        binding.searchItem.setOnTouchListener(View.OnTouchListener { v, event ->
//            val DRAWABLE_LEFT = 0
//            val DRAWABLE_TOP = 1
//            val DRAWABLE_RIGHT = 2
//            val DRAWABLE_BOTTOM = 3
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (event.rawX >= binding.searchItem.getRight() - binding.searchItem.getCompoundDrawables()
//                        .get(DRAWABLE_RIGHT).getBounds().width()
//                ) {
//                    // your action here
//                    try {
//                        var addressname = binding.searchItem.text
//                        Toast.makeText(requireContext(), " search is clicked data : ${addressname}", Toast.LENGTH_SHORT).show()
//                    }catch (
//                        e:Exception
//                    )
//                    {
//                        Toast.makeText(requireContext(), "data Invalid Taken", Toast.LENGTH_SHORT).show()
//                    }
//
//                    return@OnTouchListener true
//                }
//            }
//            true
//        })


                    return binding.root
                }

                override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    super.onViewCreated(view, savedInstanceState)

                    try {
                        getAllHotelViewModel._hotelLiveDataList.observe(
                            viewLifecycleOwner,
                            Observer {
                                when (it) {
                                    is NetworkResult.Success -> {

                                        Log.d(TAG, "response is Sucess ")
                                        val response = it.data?.hotel
                                        Log.d("response dekha", "aayo la")
                                        val recyclerView = binding.userTestHomeRecycler
                                        val hotelAdapters = TestAdapters(requireContext(), response)
                                        recyclerView.adapter = hotelAdapters
                                        recyclerView.layoutManager =
                                            LinearLayoutManager(requireContext())
                                        Log.d("Hotel Respnose Success", response.toString())
                                        hotelAdapters.setOnItemClickListner(object :
                                            TestAdapters.onItemClickListner {
                                            override fun onItemClick(position: Int) {
                                                val hotel = response!!.get(position)
                                                Log.d(TAG, "$hotel")
                                                val userId = args.user.user._id.toString()
                                                Log.d(TAG, "  user id kxa $userId")
                                                val action =
                                                    UserHomeFragmentDirections.actionUserHomeFragmentToOnTouchUserFragment(
                                                        hotel,
                                                        userId
                                                    )
                                                findNavController().navigate(action)


                                            }

                                        })

                                    }

                                    is NetworkResult.Error -> {

                                    }

                                    is NetworkResult.Loading -> {

                                    }

                                    else -> {}
                                }
                            })
                    } catch (e: Exception) {
                        Log.d(TAG, "hit vayen url")
                    }


                }

            }