package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hotelreservationsystem.Models.OtpGenerateRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerForgetPasswordBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OwnerForgetPasswordFragment : Fragment() {

lateinit var binding: FragmentOwnerForgetPasswordBinding
 private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         // Inflate the layout for this fragment
        binding = FragmentOwnerForgetPasswordBinding.inflate(layoutInflater,container,false);
        binding.sendOtp.setOnClickListener {

            val email = binding.emailAddress.text.toString();
            authViewModel.getOtp(OtpGenerateRequest(email))

            if (email.isNotEmpty()) {
//                if(isValidEmail(email)){
                    authViewModel.otpGenerateResponseLiveData.observe(viewLifecycleOwner, Observer {
                        try {
                            when (it) {


                                is NetworkResult.Success -> {
                                    try{
                                    val otp = it.data!!.otp
                                    Log.d("response","$otp")
                                        findNavController().navigate(R.id.action_ownerForgetPasswordFragment_to_ownerOtpVerificationFragment,Bundle().apply {
                                            putString("email",email.toString())
                                        })
                                }
                                    catch(E:Exception){
                                        Log.d(TAG,E.toString())
                                    }
                                }

                                is NetworkResult.Loading -> {}
                                is NetworkResult.Error -> {}
                                else -> {
                                }
                            }
                        }
                        catch (e:Exception){
                            Log.d(TAG,"out of try block in ownerforgetpassword fragment")
                        }



                    })

               }
//                //empty
                else{
                    Toast.makeText(requireContext(),"enter valid mail",Toast.LENGTH_SHORT).show()
                }
                //validity of the email is performed;


            }
            binding.backToLogin.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(R.id.action_ownerForgetPasswordFragment_to_ownerLoginFragment);
            }





        return binding.root;
    }
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}