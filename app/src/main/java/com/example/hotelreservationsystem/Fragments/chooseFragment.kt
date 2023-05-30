package com.example.hotelreservationsystem.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.databinding.FragmentChooseBinding
import com.example.hotelreservationsystem.utils.TokenManager
import com.example.hotelreservationsystem.utils.constants
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class chooseFragment : Fragment() {

    @Inject
    lateinit var  tokenManager: TokenManager
    lateinit var binding: FragmentChooseBinding
    lateinit var user: TextView;
    lateinit var owner:TextView;
    lateinit var navigation : Navigation;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseBinding.inflate(layoutInflater, container, false)



//        try {
//
//            if(tokenManager.getToken()!= null)
//            {
//                findNavController().navigate(R.id.action_chooseFragment_to_ownerHomeFragment)
//            }
//
//        }catch(e: Exception)
//        {
//            Log.d(constants.TAG,e.message.toString())
//        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.user.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_chooseFragment_to_userLoginFragment);
        }
        binding.hotelOwner.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_chooseFragment_to_ownerLoginFragment);
        }
    }


}