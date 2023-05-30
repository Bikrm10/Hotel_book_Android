package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.hotelreservationsystem.Models.OwnerOtpRequest
import com.example.hotelreservationsystem.Models.UserOtpGenerateResponse
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.UserAuthViewModel
import com.example.hotelreservationsystem.databinding.FragmentOtpVerificationBinding
import com.example.hotelreservationsystem.databinding.FragmentOwnerOtpVerificationBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class OtpVerificationFragment : Fragment() {
    lateinit var binding : FragmentOtpVerificationBinding
    private val authViewModel by viewModels<UserAuthViewModel>()
    lateinit var email : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpVerificationBinding.inflate(layoutInflater,container,false);
        email = requireArguments().getString("email").toString()
        Log.d("Email",email)

        setOtp()
        binding.continueBtn.setOnClickListener {
            verifyOtp()

        }
        return binding.root;
    }
    private fun setOtp(){
        binding.otp1.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    binding.otp2.requestFocus();
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.otp2.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty()){
                    binding.otp3.requestFocus();
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.otp3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty())
                {
                    binding.otp4.requestFocus()
                }
            }
        })
        binding.otp4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty())
                {
                    binding.otp5.requestFocus()
                }
            }
        })
        binding.otp5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty())
                {
                    binding.otp6.requestFocus()
                }
            }
        })
    }
    private fun verifyOtp(){
        val otpValue:String = binding.otp1.text.toString()+binding.otp2.text.toString()+binding.otp3.text.toString()+
                binding.otp4.text.toString()+binding.otp5.text.toString()+binding.otp6.text.toString()
        authViewModel.verifOwnerOtp(UserOtpGenerateResponse(otpValue))
        try{
            authViewModel.otpVerifyResponseLiveData.observe(viewLifecycleOwner, Observer {
                when(it){
                    is NetworkResult.Success ->{
                        val response = it.data!!.message.toString()
                        Log.d(constants.TAG,"$response")
                        if(response.toString() =="otp verification successtrue"){
                            findNavController().navigate(R.id.action_otpVerificationFragment_to_otpConfirmationFragment,Bundle().apply {
                                putString("VEmail",email)
                            })


                        }
                        else{
                            Toast.makeText(requireContext(),"Invalid otp ! ", Toast.LENGTH_SHORT).show()

                        }

                    }

                    else -> {}
                }
            })




        }
        catch(e: Exception){
            Log.d(constants.TAG,"response error")
        }


    }


}