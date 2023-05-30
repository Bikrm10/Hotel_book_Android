package com.example.hotelreservationsystem.Fragments

import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.databinding.FragmentBookNowBinding

class bookNowFragment : Fragment() {
    lateinit var  binding:FragmentBookNowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookNowBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment



        return binding.root
    }



}