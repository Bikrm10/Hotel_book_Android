package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hotelreservationsystem.Models.OwnerRequest
import com.example.hotelreservationsystem.Models.OwnerResponse
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerLoginBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.TokenManager
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class OwnerLoginFragment : Fragment() {
    lateinit var binding: FragmentOwnerLoginBinding;
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerLoginBinding.inflate(layoutInflater, container, false);



        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setting for sign in


        binding.signIn.setOnClickListener {
            val validationResult = validateOwnerInput()
            if (validationResult.first) {

                authViewModel.loginOwner(getOwnerInput())

            } else {
                Toast.makeText(requireContext(), "${validationResult.second}", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        bindObserver()

        // setting up for forgot password
        binding.forgotPassword.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_ownerLoginFragment_to_ownerForgetPasswordFragment)
        }

        // setting for sign up now
        binding.signUpNow.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_ownerLoginFragment_to_ownerRegistrationFragment)
        }

    }


    // out on view created

    private fun bindObserver() {

        authViewModel.ownerResponseLiveData.observe(viewLifecycleOwner, Observer {

            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {

                    val ownerId = it.data?.owner?._id
                    Log.d("TAG","$ownerId")

                    tokenManager.saveToken(it.data!!.access_token)
                    Log.d(TAG,it.data.access_token)

                    // trying to send the data
                    val owner = OwnerResponse(it.data!!.access_token.toString(),it.data.owner)
                    Log.d(TAG," owner ko data aauxa ta ${owner}")
                    // handling exception now
                    val ownerHotelCheck =owner.owner.hotel
                    Log.d(TAG,"hotel ma vako data $ownerHotelCheck")


                    if (ownerHotelCheck.isEmpty())
                    {
                       findNavController().navigate(R.id.action_ownerLoginFragment_to_createHotelFragment,Bundle().apply {
                           putString("ownerId",ownerId)
                       })
                    }
                    else
                    {
//                        val action =OwnerLoginFragmentDirections.actionOwnerLoginFragmentToOwnerHomeFragment(owner)
//                        findNavController().navigate(action)
                        val hotelId = it.data.owner.hotel.get(0).toString()
                         val ownerName = it.data.owner.ownername
                        findNavController().navigate(R.id.action_ownerLoginFragment_to_ownerHomeFragment,Bundle().apply {
                            putString("ownerIdFromDirect",ownerId)
                            putString("hotelIdFromDirect",hotelId)

                        })
                    }

                }


                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true

                }

            }

        })
    }

    private fun validateOwnerInput(): Pair<Boolean, String> {
        val ownerInput = getOwnerInput()

        return  authViewModel.validateCredential(ownerInput.ownername,ownerInput.email,ownerInput.password,true)


    }

    private fun getOwnerInput(): OwnerRequest {
        var ownerEmailAddress = binding.ownerEmail.text.toString()
        var ownerPassword = binding.ownerPassword.text.toString()
        return OwnerRequest(ownerEmailAddress, "", ownerPassword)
    }


}