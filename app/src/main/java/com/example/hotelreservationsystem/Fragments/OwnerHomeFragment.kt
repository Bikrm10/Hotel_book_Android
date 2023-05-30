package com.example.hotelreservationsystem.Fragments

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.Models.OwnerResponse
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerHomeBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import kotlin.math.log

@AndroidEntryPoint
class OwnerHomeFragment : Fragment() {
    lateinit var  binding :FragmentOwnerHomeBinding
    var hotelId:String? = null
    var ownerId :String? = null
    var hotel :HotelResponse? = null

   private val authViewModel by viewModels<AuthViewModel>()

   private val  hotelViewModel by viewModels<HotelViewModel> ()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerHomeBinding.inflate(layoutInflater,container,false);
        // setting ups Image Slider

        val arguments = arguments
            val hotelIdFromDirectLogin = arguments!!.getString("hotelIdFromDirect")
            val ownerIdFromDirectLogin = arguments.getString("ownerIdFromDirect")


            val hotelIdFromCreateHotel = arguments.getString("hotelIdFromCreateFragment")
            val ownerIdFromCreateFragment = arguments.getString("ownerIdFromCreateFragment")


         if(hotelIdFromDirectLogin != null && ownerIdFromDirectLogin != null)
         {
             ownerId = ownerIdFromDirectLogin
             hotelId = hotelIdFromDirectLogin

         }
        else if(hotelIdFromCreateHotel != null && ownerIdFromCreateFragment != null)
         {
             hotelId = hotelIdFromCreateHotel
             ownerId = ownerIdFromCreateFragment
         }
        else
         {
             Log.d(TAG,"Something Went wrong on getting ids ")
         }

        val FinalOwnerId = ownerId
        val FinalHotelId = hotelId
        Log.d(TAG,"Final OwnerId  is  $FinalOwnerId ")
        Log.d(TAG,"Final hotel Id  is  $FinalHotelId ")



        //yaha ma euta api call garxu


         hotelViewModel.getHotelDetails(ownerId!!,hotelId!!)
        return binding.root;
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // pahila hami observe garxum data ani matra aru funcios haru hain

        // you have to do this first

        hotelViewModel.hotelLiveData.observe(viewLifecycleOwner, Observer {
            when(it)
            {
                is NetworkResult.Success->{


                    Log.d(TAG,"owner Home ma aako data k xa ${it.data?.hotel}")
                    val hotelData = it.data!!
                    binding.hotelName.text = it.data?.hotel?.name
                    val imageAPiList :List<String> = it.data!!.hotel.photos
                    Log.d(TAG,"hotel ko images haru $imageAPiList")

                    hotel =hotelData
                    Log.d(TAG,"hotel response is  $hotel")
                    // setting  for image list
                    val imageList = ArrayList<SlideModel>() // Create image list
                    //  on image url later please pass the original images of hotel View

                    for(imageUrl in imageAPiList){

                        imageList.add(SlideModel(imageUrl,ScaleTypes.FIT))
                    }

                    binding. imageSlider.setImageList(imageList, ScaleTypes.FIT) // for all images
                    binding.imageSlider.setImageList(imageList)


                }
                is NetworkResult.Loading->{

                }
                is NetworkResult.Error->{

                }
            }
        })




        binding.addRooms.setOnClickListener {
                findNavController().navigate(
                    R.id.action_ownerHomeFragment_to_addRoomFragment,
                    Bundle().apply {
                        putString("ownerId", ownerId)
                        putString("hotelId", hotelId) })
        }





        binding.roomsList.setOnClickListener{
            findNavController().navigate(
                R.id.action_ownerHomeFragment_to_ownerRoomsFragment,
                Bundle().apply {
                    putString("ownerId", ownerId)
                    putString("hotelId", hotelId)})

        }




        binding.hotelProfile.setOnClickListener(){

                   Log.d(TAG,"Hotel profile ma jada kheri ko  hotel ko data $hotel")
                  val action = OwnerHomeFragmentDirections.actionOwnerHomeFragmentToOwnerProfileFragment(hotel!!)
                 findNavController().navigate(action)
                }


              binding.bookings.setOnClickListener(){

                  findNavController().navigate(R.id.action_ownerHomeFragment_to_ownersBookingFragment,Bundle().apply {
                      putString("ownerId", ownerId!!)
                  })
        }
        binding.logout.setOnClickListener{
            findNavController().navigate(R.id.action_ownerHomeFragment_to_ownerLoginFragment)
        }




    }


    }

