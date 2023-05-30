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
import com.example.hotelreservationsystem.Models.UserRequest
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.AuthViewModel
import com.example.hotelreservationsystem.ViewModels.UserAuthViewModel
import com.example.hotelreservationsystem.databinding.FragmentOwnerRegistrationBinding
import com.example.hotelreservationsystem.databinding.FragmentUserRegisterBinding
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegisterFragment : Fragment() {
    lateinit var binding: FragmentUserRegisterBinding;
    private val userAuthViewModel by viewModels<UserAuthViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRegisterBinding.inflate(layoutInflater,container,false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signIn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_userRegisterFragment_to_userLoginFragment)
        }

        binding.signUpButton.setOnClickListener {

            if (binding.checkBox.isChecked) {

                // calling the function validate user input
                val validationResult = validateUserInput()

                if(validationResult.first)
                {
                    userAuthViewModel.registerUser(getUserInput())
                }
                else
                {
                    Toast.makeText(requireContext(), "${validationResult.second}", Toast.LENGTH_SHORT).show()
                }

            }
        }
        bindObserver()

    }

    // out of  created view

    private fun bindObserver() {
        userAuthViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    // token management
                    //Log.d(constants.TAG,it.data!!.token)
                    findNavController().navigate(R.id.action_userRegisterFragment_to_userLoginFragment);

                }

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    //write code for progress bar
                    binding.progressBar.isVisible = true
                }
            }
        })
    }
    private  fun validateUserInput(): Pair<Boolean, String> {
        // Navigate to the owner home fragment with the data.
        val userRequest = getUserInput()
        return userAuthViewModel.validateCredential(userRequest.username,userRequest.email,userRequest.password,false)

    }
    private fun  getUserInput (): UserRequest
    {
        var userName = binding.editName.text.toString();
        var userEmail = binding.userEmail.text.toString();
        var userPassword = binding.userPassword.text.toString();
        return UserRequest(userEmail,userPassword,userName)
    }


}