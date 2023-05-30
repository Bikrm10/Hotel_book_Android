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
import com.example.hotelreservationsystem.Models.OwnerRequest
import com.example.hotelreservationsystem.Models.OwnerResponse
import com.example.hotelreservationsystem.Models.PhotosResponse
import com.example.hotelreservationsystem.Repositories.OwnerRepository
import com.example.hotelreservationsystem.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private  val ownerRepository: OwnerRepository):ViewModel() {


    //  getting the live data value
    val ownerResponseLiveData:LiveData<NetworkResult<OwnerResponse>>
        get() = ownerRepository.ownerResponseLiveData

    val photoResonseLiveData:LiveData<NetworkResult<PhotosResponse>>
    get()= ownerRepository.photoResponseLiveData

     val otpGenerateResponseLiveData : LiveData<NetworkResult<OtpGenerateResponse>>
        get() = ownerRepository.otpGenerateResponseLiveData
    val otpVerifyResponseLiveData:LiveData<NetworkResult<OwnerOtpResponse>>
        get()=ownerRepository.otpVerifyResponseLiveData
    val confirmOwnerPasswordLivedata : LiveData<NetworkResult<ConfirmOwnerPasswordResponse>>
        get() = ownerRepository.confirmOwnerPasswordLiveData
    fun registerOwner(ownerRequest: OwnerRequest) {
        viewModelScope.launch {
            ownerRepository.registerOwner(ownerRequest)
        }
    }

    fun uploadImage(image:MultipartBody.Part)
    {
        viewModelScope.launch {
            ownerRepository.uploadImage(image)
        }

    }

    fun loginOwner(ownerRequest: OwnerRequest) {
        viewModelScope.launch {
            ownerRepository.loginOwner(ownerRequest)
        }

    }
    fun getOtp(otpGenerateRequest: OtpGenerateRequest){
        viewModelScope.launch {
            ownerRepository.getOtp(otpGenerateRequest)
        }
    }
    fun verifOwnerOtp(otpRequest: OwnerOtpRequest){
        viewModelScope.launch {
            ownerRepository.verifyOwnerOtp(otpRequest)
        }
    }
    fun createOwnerPassword(confirmOwnerPasswordRequest: ConfirmOwnerPasswordRequest){
        viewModelScope.launch {
            ownerRepository.createOwnerPassword(confirmOwnerPasswordRequest)
        }
    }

    fun validateCredential(ownerName:String, ownerEmailAddress:String, ownerPassword:String, isOwnerLoginFragment:Boolean):Pair<Boolean,String>
    {
        var result = Pair(true,"")

        if( !isOwnerLoginFragment && TextUtils.isEmpty(ownerName)  || TextUtils.isEmpty(ownerEmailAddress)  || TextUtils.isEmpty(ownerPassword))
        {
            result = Pair(false,"Please provide the credential")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(ownerEmailAddress).matches())
        {
            result = Pair(false,"Please enter Valid EmailAddress")
        }

        else if(ownerPassword.length  <= 5)
        {
             result = Pair(false,"Password length should be grater than 6")
        }
        return  result

    }
}




