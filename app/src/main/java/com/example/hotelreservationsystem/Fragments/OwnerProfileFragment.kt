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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.hotelreservationsystem.Models.HotelRequest
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.Models.OwnerResponse
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerProfileBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

import kotlin.Exception


@AndroidEntryPoint

class OwnerProfileFragment : Fragment() {
    private  val args by navArgs<OwnerProfileFragmentArgs>()

    lateinit var binding: FragmentOwnerProfileBinding
    var ownerId: String? = null
    var hotelId :String?  = null
    lateinit var imageUri: Uri
    lateinit var imagePath: String


    var typeSTR:String? = null
    var typeINT :Int? = null
    var finaltypeInt:Int? = null


    private val authViewModel by viewModels<AuthViewModel>()
    private val hotelViewModel by viewModels<HotelViewModel>()


    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
//        binding.image1.setImageURI(it)

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
        Log.d(TAG, "when call from the hotel create Fragment ${part.toString()}")

        authViewModel.uploadImage(part)
        authViewModel.photoResonseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    try {
                        Log.d(TAG, "Show me the image uri  of  hotel images ${it.data?.url}")
                        imagePath = it.data!!.url
                        Log.d(TAG, "k xa ta image path ma  $imagePath")

                        this.context?.let { it1 -> Glide.with(it1).load(imageUri).into(binding.image1) }

                        binding.updateHotel.visibility = View.VISIBLE

                    } catch (e: Exception) {
                    }
                    Log.d(TAG, "Image path Getting Error")
                }

                is NetworkResult.Error -> {
                }

                is NetworkResult.Loading -> {

                }
            }

        })


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerProfileBinding.inflate(
            layoutInflater, container, false
        )
        // Inflate the layout for this fragment
        ownerId = args.hotel.hotel.owner._id.toString()
        hotelId = args.hotel.hotel._id.toString()

        // setting for autocompleteText Listner
        val itemsselecor = resources.getStringArray(R.array.hotelType);
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, itemsselecor);
        binding.autocomplete.setAdapter(arrayAdapter)

        binding.autocomplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val text = parent.getItemAtPosition(position);
                Log.d(TAG,"okay selected Text is ${binding.autocomplete.text}")

            }


        binding.addImage1.setOnClickListener {
            contract.launch("image/*")

        }


        binding.updateHotel.setOnClickListener{



            var name: String = binding.hotelName.text.toString()
            var addresses: String = binding.hotelLocation.text.toString()
            var description: String = binding.hotelDescription.text.toString()
            var image: String = imagePath.toString()
            val priceInt:Int = Integer.parseInt(binding.minimumCharge.text.toString())
            //lets seleect the int for values
            var hotelType = binding.autocomplete.text.toString()
            // <item>Luxury Hotel</item>
            //        <item>Budget Hotel</item>
            //        <item>Business Hotel</item>
            if(hotelType == "Luxury Hotel")
            {
                typeSTR = "1"
                typeINT = Integer.parseInt(typeSTR)
            }
            else if(hotelType == "Budget Hotel")
            {
                typeSTR = "2"
                typeINT=Integer.parseInt(typeSTR)
            }
            else
            {
                typeSTR = "3"
                typeINT=Integer.parseInt(typeSTR)
            }
            val finalHotelInt:Int = typeINT!!
            Log.d(TAG," Final int is $finalHotelInt")
            Log.d(TAG," Final int is $priceInt")

            try {
                hotelViewModel.updatehotel(ownerId!!,hotelId!!,
                    HotelRequest(addresses,description,name,image,finalHotelInt,priceInt))
            }
            catch (e:java.lang.Exception)
            {
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



     hotelViewModel.hotelLiveData.observe( viewLifecycleOwner, Observer {
         when(it)
         {
             is NetworkResult.Success->{
                 val updatedResponse = it.data
                 Log.d(TAG," updated response aayo $updatedResponse")
                 findNavController().popBackStack()
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

