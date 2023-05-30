package com.example.hotelreservationsystem.Repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordRequest
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordResponse
import com.example.hotelreservationsystem.Models.OtpGenerateRequest
import com.example.hotelreservationsystem.Models.OtpGenerateResponse
import com.example.hotelreservationsystem.Models.OwnerOtpRequest
import com.example.hotelreservationsystem.Models.OwnerOtpResponse
import com.example.hotelreservationsystem.Models.OwnerRequest
import com.example.hotelreservationsystem.Models.OwnerResponse
import com.example.hotelreservationsystem.Models.PhotosResponse
import com.example.hotelreservationsystem.api.OwnerApi
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import kotlin.Exception

class OwnerRepository  @Inject constructor ( private val ownerApi:OwnerApi) {

    private val _photosResponseLiveData = MutableLiveData<NetworkResult<PhotosResponse>>()
    val photoResponseLiveData: LiveData<NetworkResult<PhotosResponse>>
        get() = _photosResponseLiveData

    //setting of live data
    private val _ownerResponseLiveData = MutableLiveData<NetworkResult<OwnerResponse>>()
    val ownerResponseLiveData: LiveData<NetworkResult<OwnerResponse>>
        get() = _ownerResponseLiveData

    private val _otpGenerateResponseLiveData = MutableLiveData<NetworkResult<OtpGenerateResponse>>()
    val otpGenerateResponseLiveData: LiveData<NetworkResult<OtpGenerateResponse>>
        get() = _otpGenerateResponseLiveData

    private val _otpVerifyResponseLiveData = MutableLiveData<NetworkResult<OwnerOtpResponse>>()
    val otpVerifyResponseLiveData : LiveData<NetworkResult<OwnerOtpResponse>>
        get()=_otpVerifyResponseLiveData
    private val _confirmOwnerPasswordLiveData = MutableLiveData<NetworkResult<ConfirmOwnerPasswordResponse>>()
    val confirmOwnerPasswordLiveData : LiveData<NetworkResult<ConfirmOwnerPasswordResponse>>
        get() = _confirmOwnerPasswordLiveData

    // repos Function that calls owner api functions

    suspend fun registerOwner(ownerRequest: OwnerRequest) {
        _ownerResponseLiveData.postValue(NetworkResult.Loading())
        val response = ownerApi.signUp(ownerRequest)

        handleResponse(response)
    }

    suspend fun uploadImage(image: MultipartBody.Part) {
        val response = ownerApi.uplaodImage(image)
        if (response.isSuccessful && response.body() != null) {
            _photosResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _photosResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("error")))
        }
    }

    suspend fun loginOwner(ownerRequest: OwnerRequest) {
        _ownerResponseLiveData.postValue(NetworkResult.Loading())
        val response = ownerApi.signIn(ownerRequest)
        handleResponse(response)
    }

    private fun handleResponse(response: Response<OwnerResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _ownerResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _ownerResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("error")))
        } else {
            _ownerResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    suspend fun getOtp(otpGenerateRequest: OtpGenerateRequest) {
        Log.d(TAG,"response lyauney thaw ")

        try {
            val response = ownerApi.getOtp(otpGenerateRequest)
            if (response.isSuccessful && response.body() != null) {
            Log.d("response","response generated")
            _otpGenerateResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else
        {
            Log.d(TAG,"error on getting the response")
        }

        }
        catch (e:Exception)

        {
            Log.d(TAG,"there is error in repsonse from api")
        }


    }
    suspend fun verifyOwnerOtp(otpRequest: OwnerOtpRequest){
        try{
            val response = ownerApi.verifyOwnerOtp(otpRequest)
            if(response.isSuccessful && response.body()!==null) {
                _otpVerifyResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            else{
                Log.d(TAG,"OtpResponse error")
            }

        }
        catch(e:Exception){
            Log.d("ownerOtpException","Esception in getting otp response")
        }
    }
    suspend fun createOwnerPassword(confirmOwnerPasswordRequest: ConfirmOwnerPasswordRequest){
        val response = ownerApi.createOwnerPassword(confirmOwnerPasswordRequest)
        try {


            if(response.isSuccessful&&response.body()!==null){
                Log.d(TAG," create passwoerd garda ko response ${response.body()}")
                _confirmOwnerPasswordLiveData.postValue(NetworkResult.Success(response.body()!!))
            }

        }
        catch (e:Exception)
        {
            Log.d(TAG,e.toString())
        }


    }
}