package com.example.hotelreservationsystem.Fragments

import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.whenResumed
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.Models.RoomRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentUpdateRoomBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import kotlin.math.log

@AndroidEntryPoint
class updateRoomFragment : Fragment() {

    lateinit var  binding: FragmentUpdateRoomBinding
    private val  hotelViewModel by viewModels<HotelViewModel>()

    var ownerId: String? = null
    var hotelid: String? = null
    var roomId:String?= null

// private val args by navArgs<OwnerHomeFragmentArgs>()
    private val args by navArgs<updateRoomFragmentArgs>()
    lateinit var imageUri: Uri
    lateinit var imagePath: String
    private val authViewModel by viewModels<AuthViewModel>()

// function for getting the images

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        binding.image1.setImageURI(it)
        // converting the image
        val filesDir = requireContext().filesDir
        val file = File(filesDir, "image.png")
        val resolver = context?.contentResolver
        val inputStream = resolver?.openInputStream(imageUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("photos", file.name, requestBody)

        Log.d(TAG, imageUri.toString())
        Log.d(TAG, part.toString())

        // image converted

        // getting the url of the image

        authViewModel.uploadImage(part)
        authViewModel.photoResonseLiveData.observe(viewLifecycleOwner, Observer {

            when (it) {
                is NetworkResult.Success -> {
                    try {
                        Log.d(TAG, "Show me the image uri ${it.data?.url}")
                        imagePath = it.data!!.url
                    }
                    catch (e:Exception)
                    {
                        Log.d(TAG,"Error time")
                    }

                }

                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {
                }
            }
        })

    }




    // okay function end

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = FragmentUpdateRoomBinding.inflate(layoutInflater,container,false)


        // Work  upder this fragment


         ownerId = args.roomDetails.owner
          hotelid = args.roomDetails.hotel
          roomId = args.roomDetails._id



    Log.d(TAG, "okay  lets rechake the id  owner  $ownerId hotelId $hotelid  room id $roomId  ")



        // callling function to delete the room
         binding.deleteInsideRoomcard.setOnClickListener{
             hotelViewModel.deleteRoom(ownerId!!,hotelid!!,roomId!!)
            hotelViewModel.hotelLiveData.observe(viewLifecycleOwner, Observer {
                when(it)
                {
                    is NetworkResult.Success->
                    {
                        try {
                            Toast.makeText(requireContext(), "Room Deleated SucessFully", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                        catch (
                            e:Exception
                        )
                        {
                            Toast.makeText(requireContext(), "Sorry Can Not perform ", Toast.LENGTH_SHORT).show()
                        }


                    }
                     is NetworkResult.Error->{

                     }
                    is NetworkResult.Loading->{

                    }
                }
            })
         }


        val itemsselecor = resources.getStringArray(R.array.selectors);
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, itemsselecor);
        binding.autocomplete.setAdapter(arrayAdapter)

        binding.autocomplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val text = parent.getItemAtPosition(position);
            }


        binding.addImage1.setOnClickListener {
            contract.launch("image/*")
        }


        // code to update room


        binding.updateRoomFrag.setOnClickListener() {

            var number = binding.roomNumber.text.toString()
            var roomType = binding.autocomplete.text.toString()
            var price = binding.roomRent.text.toString()
            var uri = imagePath.toString()
            // i have to take the int value

            val numberInt = Integer.parseInt(number)
            val priceInt = Integer.parseInt(price)
            // checking for the error
            Log.d(TAG,"$numberInt")
            Log.d(TAG,"$priceInt")

            //String value= et.getText().toString();
            //int finalValue=Integer.parseInt(value);

            Log.d(TAG,uri)

            Log.d(TAG,"OKAY DATA ARE  $numberInt $roomType $price $uri ")

            try {
                hotelViewModel.updateRoom(ownerId!!,hotelid!!,roomId!!,RoomRequest(numberInt,priceInt,roomType,uri))
            }
            catch (e:Exception)
            {
                Log.d(TAG,"error on calling the api response na aako bela ")
            }


        }

        hotelViewModel.hotelLiveData.observe(viewLifecycleOwner, Observer {
            when(it)
            {
                is NetworkResult.Success->
                {
                    Toast.makeText(requireContext(), "Hotel Updated Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is NetworkResult.Loading->{

                }
                is NetworkResult.Error->{

                }
            }
        })


        //update room Finsihed






        return binding.root
    }

}


