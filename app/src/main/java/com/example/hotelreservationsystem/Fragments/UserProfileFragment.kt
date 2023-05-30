package com.example.hotelreservationsystem.Fragments
import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hotelreservationsystem.R
import com.example.hotelreservationsystem.ViewModels.GetAllHotelViewModel
import com.example.hotelreservationsystem.databinding.FragmentUserProfileBinding
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import me.ibrahimsn.lib.SmoothBottomBar

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    lateinit var binding  : FragmentUserProfileBinding

    lateinit var profileExpandable: ConstraintLayout
    var userId:String?= null

    private val getAllHotelViewModel by viewModels<GetAllHotelViewModel>()
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(layoutInflater, container, false)
        // setting the dropdown function
        userId = requireArguments().getString("userId")
        Log.d(TAG,"user  id in userProfile  is $userId")

        profileExpandable = binding.expandableView
        binding.profileDropdown.setOnClickListener {
            if (profileExpandable.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                profileExpandable.visibility = View.VISIBLE
                binding.profileDropdown.setImageResource(R.drawable.arrow_up)

            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                profileExpandable.visibility = View.GONE
                binding.profileDropdown.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                //
            }
        }
        binding.helpAndSupport.setOnClickListener {

            if (binding.helpAndSupportConstraint.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.helpAndSupportConstraint.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.helpAndSupportConstraint.visibility = View.GONE
            }
        }
        binding.privacytab.setOnClickListener {

            if (binding.securityAndPrivacyConstraint.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.securityAndPrivacyConstraint.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.securityAndPrivacyConstraint.visibility = View.GONE
            }
        }

        binding.history.setOnClickListener{

          findNavController().navigate(R.id.action_userProfileFragment_to_userHistoryFragment,Bundle().apply {
              putString("userId",userId)
          })

        }
        binding.logOutCard.setOnClickListener{
            findNavController().navigate(R.id.action_userProfileFragment_to_userLoginFragment)

        }



        return binding.root
    }

}

