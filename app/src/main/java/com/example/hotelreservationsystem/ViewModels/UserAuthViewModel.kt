package com.example.hotelreservationsystem.ViewModels

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordRequest
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordResponse
import com.example.hotelreservationsystem.Models.OtpGenerateRequest
import com.example.hotelreservationsystem.Models.OtpGenerateResponse
import com.example.hotelreservationsystem.Models.OwnerOtpRequest
import com.example.hotelreservationsystem.Models.OwnerOtpResponse
import com.example.hotelreservationsystem.Models.UserOtpGenerateRequest
import com.example.hotelreservationsystem.Models.UserOtpGenerateResponse
import com.example.hotelreservationsystem.Models.UserRequest
import com.example.hotelreservationsystem.Models.UserResponse
import com.example.hotelreservationsystem.Models.UserSavePasswordResponse
import com.example.hotelreservationsystem.Models.UserVerifyOtpResponse
import com.example.hotelreservationsystem.Repositories.UserRepository
import com.example.hotelreservationsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserAuthViewModel @Inject constructor(private  val userRepository: UserRepository): ViewModel(){

    //  getting the live data value
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    val otpGenerateResponseLiveData : LiveData<NetworkResult<UserOtpGenerateResponse>>
        get() = userRepository.otpGenerateResponseLiveData
    val otpVerifyResponseLiveData:LiveData<NetworkResult<UserVerifyOtpResponse>>
        get()=userRepository.otpVerifyResponseLiveData
    val confirmOwnerPasswordLivedata : LiveData<NetworkResult<UserSavePasswordResponse>>
        get() = userRepository.confirmOwnerPasswordLiveData
    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.registerUser(userRequest)
        }
    }

    fun loginUser(userRequest: UserRequest) {
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }

    }
    fun getOtp(userOtpGenerateRequest: UserOtpGenerateRequest){
        viewModelScope.launch {
            userRepository.getOtp(userOtpGenerateRequest)
        }
    }
    fun verifOwnerOtp(userOtpGenerateResponse: UserOtpGenerateResponse){
        viewModelScope.launch {
            userRepository.verifyOwnerOtp(userOtpGenerateResponse)
        }
    }
    fun createOwnerPassword(confirmOwnerPasswordRequest: ConfirmOwnerPasswordRequest){
        viewModelScope.launch {
            userRepository.createOwnerPassword(confirmOwnerPasswordRequest)
        }
    }

    fun validateCredential(userName:String, userEmailAddress:String, userPassword:String, isUserLoginFragment:Boolean):Pair<Boolean,String>
    {
        var result = Pair(true,"")

        if( !isUserLoginFragment && TextUtils.isEmpty(userName)  || TextUtils.isEmpty(userEmailAddress)  || TextUtils.isEmpty(userPassword))
        {
            result = Pair(false,"Please provide the credential")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(userEmailAddress).matches())
        {
            result = Pair(false,"Please enter Valid EmailAddress")
        }

        else if(userPassword.length  <= 6)
        {
            result = Pair(false,"Password length should be grater than 6")
        }
        return  result

    }
}

