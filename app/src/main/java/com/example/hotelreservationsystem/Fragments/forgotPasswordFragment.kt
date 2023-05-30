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
import com.example.hotelreservationsystem.Models.OtpGenerateRequest
import com.example.hotelreservationsystem.Models.UserOtpGenerateRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.UserAuthViewModel
import com.example.hotelreservationsystem.databinding.FragmentForgotPasswordBinding
import com.example.hotelreservationsystem.databinding.FragmentOwnerForgetPasswordBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class forgotPasswordFragment : Fragment() {
    lateinit var binding: FragmentForgotPasswordBinding
    private val authViewModel by viewModels<UserAuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater,container,false);
        binding.sendOtp.setOnClickListener {

            val email = binding.emailAddress.text.toString();
            authViewModel.getOtp(UserOtpGenerateRequest(email))

            if (email.isNotEmpty()) {
//                if(isValidEmail(email)){
                authViewModel.otpGenerateResponseLiveData.observe(viewLifecycleOwner, Observer {
                    try {
                        when (it) {


                            is NetworkResult.Success -> {
                                try{
                                    val otp = it.data!!.otp
                                    Log.d("response","$otp")
                                    findNavController().navigate(R.id.action_forgotPasswordFragment_to_otpVerificationFragment,Bundle().apply {
                                        putString("email",email.toString())
                                    })
                                }
                                catch(E:Exception){
                                    Log.d(constants.TAG,E.toString())
                                }
                            }

                            is NetworkResult.Loading -> {}
                            is NetworkResult.Error -> {}
                            else -> {
                            }
                        }
                    }
                    catch (e:Exception){
                        Log.d(constants.TAG,"out of try block in userforget password fragment")
                    }



                })

            }
//                //empty
            else{
                Toast.makeText(requireContext(),"enter valid mail", Toast.LENGTH_SHORT).show()
            }
            //validity of the email is performed;


        }

        binding.backToLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_forgotPasswordFragment_to_userLoginFragment);
        }

        return binding.root;
    }


}