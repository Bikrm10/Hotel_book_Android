package com.example.hotelreservationsystem.Fragments

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
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerOtpConfirmationBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerOtpConfirmationFragment : Fragment() {
    lateinit var binding: FragmentOwnerOtpConfirmationBinding;
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOwnerOtpConfirmationBinding.inflate(layoutInflater, container, false);
        val email = requireArguments().getString("VEmail").toString()
        Log.d("123",email)
//        val psd= binding.createPassword.text.toString()
//        val cPsd = binding.confirmPassword.text.toString()
//
////        val confirmOwnerPasswordRequest = ConfirmOwnerPasswordRequest( confirmPassword,email, password)
////        authViewModel.createOwnerPassword(ConfirmOwnerPasswordRequest(confirmPassword,email,password))
//        Log.d("password","$psd,$cPsd,$email")

        binding.update.setOnClickListener {
            val psd= binding.createPassword.text.toString()
            val cPsd = binding.confirmPassword.text.toString()
            Log.d("password","$psd,$cPsd,$email" )

//        val confirmOwnerPasswordRequest = ConfirmOwnerPasswordRequest( confirmPassword,email, password)
        authViewModel.createOwnerPassword(ConfirmOwnerPasswordRequest(cPsd,email,psd))



            authViewModel.confirmOwnerPasswordLivedata.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is NetworkResult.Success -> {

                        val response = it.data?.message
                        Log.d("rettt", "$response")
                        if(response =="password changed successfully") {


                          findNavController().navigate(R.id.action_ownerOtpConfirmationFragment_to_ownerLoginFragment)
                        }
                        else{
                            Toast.makeText(requireContext(),"not changed",Toast.LENGTH_SHORT).show()
                        }
                    }

                    is NetworkResult.Loading -> {}
                    is NetworkResult.Error -> {}
                    else -> {

                    }
                }
            })
        }




            // updating password  is in this field
//            Navigation.findNavController(it)
//                .navigate(R.id.action_ownerOtpConfirmationFragment_to_ownerLoginFragment);


        return binding.root

    }
}