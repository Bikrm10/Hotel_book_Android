package com.example.hotelreservationsystem.Fragments

import android.icu.number.IntegerWidth
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
import com.bumptech.glide.Glide
import com.example.hotelreservationsystem.Models.HotelRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentCreateHotelBinding
import com.example.hotelreservationsystem.databinding.FragmentOwnerProfileBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class CreateHotelFragment : Fragment() {
    lateinit var binding :FragmentCreateHotelBinding

    var ownerId: String? = null

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

        Log.d(constants.TAG, imageUri.toString())
        Log.d(constants.TAG, "when call from the hotel create Fragment ${part.toString()}")

        authViewModel.uploadImage(part)
        authViewModel.photoResonseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    try {
                        Log.d(constants.TAG, "Show me the image uri  of  hotel images ${it.data?.url}")
                        imagePath = it.data!!.url
                        Log.d(constants.TAG, "k xa ta image path ma  $imagePath")
                        this.context?.let { it1 -> Glide.with(it1).load(imageUri).into(binding.image1) }

                        binding.createHotel.visibility = View.VISIBLE

                    } catch (e: Exception) {
                    }
                    Log.d(constants.TAG, "Image path Getting Error")
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
        // Inflate the layout for this fragment
        binding = FragmentCreateHotelBinding.inflate(layoutInflater,container,false)


        // kaam yaha baat
         // hotel id pathaunu paro bass hain ta kin vaney owner id pahiley dekhin kee xa ma sanga


        //acessing the sent owner id from the data

        ownerId = requireArguments().getString("ownerId").toString()
        Log.d(TAG,"Owner id  is $ownerId")

        binding.addImage1.setOnClickListener {
            contract.launch("image/*")

        }


        val itemsselecor = resources.getStringArray(R.array.hotelType);
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.list_item, itemsselecor);
        binding.autocomplete.setAdapter(arrayAdapter)

        binding.autocomplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val text = parent.getItemAtPosition(position);
                Log.d(TAG,"okay selected Text is ${binding.autocomplete.text}")

            }






        binding.createHotel.setOnClickListener {

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
                hotelViewModel.createHotel(ownerId!!,
                    HotelRequest(addresses,description,name,image,finalHotelInt,priceInt)
                )
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
            }

        }

        hotelViewModel.hotelLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {

                is NetworkResult.Success -> {
                    Log.d(constants.TAG, "Hotel Created Sucessfully")
                    Log.d(TAG," hotel Response ${it.data?.hotel}")
                    val hotelIdFromCreate = it.data?.hotel?._id
                    val ownerIdFromCreate = it.data?.hotel?.owner?._id
                    Log.d(TAG,"create home fragment ko id $hotelIdFromCreate")

                    //  /  val owner = OwnerResponse(it.data!!.access_token.toString(),it.data.owner)
                      findNavController().navigate(R.id.action_createHotelFragment_to_ownerHomeFragment,Bundle().apply {
                          putString("hotelIdFromCreateFragment",hotelIdFromCreate)
                          putString("ownerIdFromCreateFragment",ownerIdFromCreate)
                      })

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true


                }

                is NetworkResult.Error -> {


                }
            }
        })



        return binding.root
    }



}