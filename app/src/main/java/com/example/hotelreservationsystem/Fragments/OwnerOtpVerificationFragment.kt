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
import androidx.navigation.NavArgs
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotelreservationsystem.Models.OwnerOtpRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerOtpVerificationBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class OwnerOtpVerificationFragment : Fragment() {
lateinit var binding :FragmentOwnerOtpVerificationBinding

   private val authViewModel by viewModels<AuthViewModel>()
    lateinit var email : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        email = requireArguments().getString("email").toString()
        Log.d("Email",email)
        binding = FragmentOwnerOtpVerificationBinding.inflate(layoutInflater,container,false);

        setOtp();
        binding.continueBtn.setOnClickListener {
            verifyOtp()

        }
        return binding.root;
    }
     private fun setOtp(){
        binding.otp1.addTextChangedListener(object:TextWatcher{
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
        binding.otp2.addTextChangedListener(object:TextWatcher{
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
                 authViewModel.verifOwnerOtp(OwnerOtpRequest(otpValue))
                try{
                     authViewModel.otpVerifyResponseLiveData.observe(viewLifecycleOwner, Observer {
                        when(it){
                            is NetworkResult.Success ->{
                                val response = it.data!!.message.toString()
                                Log.d(TAG,"$response")
                                if(response.toString() =="otp verification successtrue"){
                                    findNavController().navigate(R.id.action_ownerOtpVerificationFragment_to_ownerOtpConfirmationFragment,Bundle().apply {
                                        putString("VEmail",email)
                                    })


                                }
                                else{
                                    Toast.makeText(requireContext(),"Invalid otp ! ",Toast.LENGTH_SHORT).show()

                        }

                    }

                    else -> {}
                }
            })




        }
        catch(e:Exception){
            Log.d(TAG,"response error")
        }


    }


}