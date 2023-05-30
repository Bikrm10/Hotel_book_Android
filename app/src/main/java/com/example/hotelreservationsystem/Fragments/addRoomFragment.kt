package com.example.hotelreservationsystem.Fragments
import android.icu.number.IntegerWidth
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.Models.RoomRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.HotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentAddRoomBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import kotlin.math.log


@AndroidEntryPoint
class addRoomFragment : Fragment() {
    lateinit var binding:FragmentAddRoomBinding

    var ownerId: String? = null
    var hoteid: String? = null

    lateinit var imageUri: Uri
     lateinit var imagePath: String

    private val hotelViewModel by viewModels<HotelViewModel>()

    private val authViewModel by viewModels<AuthViewModel>()


    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        // changed code
      //  binding.image1.setImageURI(it)
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
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {

                    try {
                        Log.d(TAG, "Show me the image uri ${it.data?.url}")
                        imagePath = it.data!!.url


                        //changed code
                        // binding.image1.setImageURI(it)
                      //  context?.let { it1 -> Glide.with(it1).load(imagePath) }

                       // Glide.with(this.context).load(R.drawable.test).into(holder.room_view_image)
                        this.context?.let { it1 ->
                            Glide.with(it1).load(imageUri).into(binding.image1)
                        }
                        binding.createRoom.visibility =View.VISIBLE


                    }
                    catch (e:Exception)
                    {
                        Log.d(TAG,"Error time")
                    }

                }

                is NetworkResult.Error -> {

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddRoomBinding.inflate(layoutInflater, container, false)
        // setting for dropdowns

// getting the owner id and hotel id from passsng the argumnet  from owner home fragment
        ownerId = requireArguments().getString("ownerId").toString()
        hoteid = requireArguments().getString("hotelId").toString()



        Log.d(TAG,"ownerid and hotelId  $ownerId $hoteid")



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

        binding.createRoom.setOnClickListener() {

                var number = binding.roomNumber.text.toString()
                var roomType = binding.autocomplete.text.toString()
                var price = binding.roomRent.text.toString()
                var uri = imagePath.toString()
            // i have to take the int value
                val numberInt = Integer.parseInt(number)
                val priceInt = Integer.parseInt(price)
                Log.d(TAG,"$numberInt")
                Log.d(TAG,"$priceInt")
                //String value= et.getText().toString();
                //int finalValue=Integer.parseInt(value);

                Log.d(TAG,uri)




                      hotelViewModel.addRoom(ownerId!!, hoteid!!, RoomRequest(numberInt, priceInt, roomType, uri))






        }
       hotelViewModel.hotelLiveData.observe( viewLifecycleOwner, Observer {
           when(it)
           {
               is NetworkResult.Success->{
                   val hotelIdIn = it.data?.hotel?._id
                   Log.d(TAG,"room id id $hotelIdIn")
                   val hotelData = HotelResponse(it.data?.hotel!!)
                   Log.d(TAG,hotelData.toString())
//                   val action =addRoomFragmentDirections.actionAddRoomFragmentToOwnerRoomsFragment(hotelData)
//                   findNavController().navigate(action)
                   findNavController().popBackStack()
               }
               is NetworkResult.Error->{}
               is NetworkResult.Loading ->{

               }
           }
       })

        // Inflate the layout for this fragment
        return binding.root
    }


}