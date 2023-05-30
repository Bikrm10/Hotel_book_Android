package com.example.hotelreservationsystem.Repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordRequest
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordResponse
import com.example.hotelreservationsystem.Models.HotelResponse
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
import com.example.hotelreservationsystem.api.UserApi
import com.example.hotelreservationsystem.api.getallHotelsApi
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor( private val userApi: UserApi){
    // setting live data



    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData: LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData


    private val _otpGenerateResponseLiveData = MutableLiveData<NetworkResult<UserOtpGenerateResponse>>()
    val otpGenerateResponseLiveData: LiveData<NetworkResult<UserOtpGenerateResponse>>
        get() = _otpGenerateResponseLiveData


    private val _otpVerifyResponseLiveData = MutableLiveData<NetworkResult<UserVerifyOtpResponse>>()
    val otpVerifyResponseLiveData : LiveData<NetworkResult<UserVerifyOtpResponse>>
        get()=_otpVerifyResponseLiveData


    private val _confirmOwnerPasswordLiveData = MutableLiveData<NetworkResult<UserSavePasswordResponse>>()
    val confirmOwnerPasswordLiveData : LiveData<NetworkResult<UserSavePasswordResponse>>
        get() = _confirmOwnerPasswordLiveData



    // repos Function that calls owner api functions

    suspend fun registerUser(userRequest: UserRequest)
    {
        _userResponseLiveData.postValue(NetworkResult.Loading())

        val response = userApi.signUp(userRequest)
        handleResponse(response)
    }
    suspend fun  loginUser(userRequest: UserRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())

        val response = userApi.signIn(userRequest)

        handleResponse(response)
    }
    //suspend fun getAllHotel(userId: String) {}

    suspend fun getOtp(userOtpGenerateRequest: UserOtpGenerateRequest) {
        Log.d(constants.TAG,"response lyauney thaw ")

        try {
            val response = userApi.generateOtp(userOtpGenerateRequest)
            if (response.isSuccessful && response.body() != null) {
                Log.d("response","response generated")
                _otpGenerateResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            else
            {
                Log.d(constants.TAG,"error on getting the response")
            }

        }
        catch (e:Exception)

        {
            Log.d(constants.TAG,"there is error in repsonse from api")
        }


    }
    suspend fun verifyOwnerOtp(userOtpGenerateResponse: UserOtpGenerateResponse){
        try{
            val response = userApi.verifyOwnerOtp(userOtpGenerateResponse)
            if(response.isSuccessful && response.body()!==null) {
                _otpVerifyResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
            }
            else{
                Log.d(constants.TAG,"OtpResponse error")
            }

        }
        catch(e:Exception){
            Log.d("ownerOtpException","Esception in getting otp response")
        }
    }
    suspend fun createOwnerPassword(confirmOwnerPasswordRequest: ConfirmOwnerPasswordRequest){
        val response = userApi.createOwnerPassword(confirmOwnerPasswordRequest)
        try {


            if(response.isSuccessful&&response.body()!==null){
                Log.d(constants.TAG," create passwoerd garda ko response ${response.body()}")
                _confirmOwnerPasswordLiveData.postValue(NetworkResult.Success(response.body()!!))
            }

        }
        catch (e:Exception)
        {
            Log.d(constants.TAG,e.toString())
        }


    }


    private fun handleResponse(response: Response<UserResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponseLiveData.postValue(NetworkResult.Error(errorObj.getString("error")))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

}
