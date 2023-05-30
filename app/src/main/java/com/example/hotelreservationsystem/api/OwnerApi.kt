package com.example.hotelreservationsystem.api

import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordRequest
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordResponse
import com.example.hotelreservationsystem.Models.OtpGenerateRequest
import com.example.hotelreservationsystem.Models.OtpGenerateResponse
import com.example.hotelreservationsystem.Models.OwnerOtpRequest
import com.example.hotelreservationsystem.Models.OwnerOtpResponse
import com.example.hotelreservationsystem.Models.OwnerRequest
import com.example.hotelreservationsystem.Models.OwnerResponse
import com.example.hotelreservationsystem.Models.PhotosResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface OwnerApi {

     @POST("/ownerauth/register1")
    suspend fun signUp( @Body ownerRequest: OwnerRequest):Response<OwnerResponse>

    @POST("/ownerauth/login1")
    suspend fun signIn( @Body ownerRequest: OwnerRequest):Response<OwnerResponse>
     @Multipart
    @POST("/ownerroom/uploadroompictocloud")
    suspend fun uplaodImage(@Part image:MultipartBody.Part):Response<PhotosResponse>


    @POST("ownerauth/generateotp1")
    suspend fun getOtp(@Body otpGenerateRequest: OtpGenerateRequest):Response<OtpGenerateResponse>

    @POST("ownerauth/verifyotp1")
    suspend fun verifyOwnerOtp(@Body ownerOtpRequest: OwnerOtpRequest):Response<OwnerOtpResponse>

    @POST("ownerauth/savepassword1")
    suspend fun createOwnerPassword(@Body confirmOwnerPasswordRequest: ConfirmOwnerPasswordRequest): Response<ConfirmOwnerPasswordResponse>

    }

