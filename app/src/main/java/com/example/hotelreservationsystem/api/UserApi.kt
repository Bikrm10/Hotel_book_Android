package com.example.hotelreservationsystem.api

import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordRequest
import com.example.hotelreservationsystem.Models.ConfirmOwnerPasswordResponse
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.Models.OwnerOtpRequest
import com.example.hotelreservationsystem.Models.OwnerOtpResponse
import com.example.hotelreservationsystem.Models.UserOtpGenerateRequest
import com.example.hotelreservationsystem.Models.UserOtpGenerateResponse
import com.example.hotelreservationsystem.Models.UserRequest
import com.example.hotelreservationsystem.Models.UserResponse
import com.example.hotelreservationsystem.Models.UserSavePasswordResponse
import com.example.hotelreservationsystem.Models.UserVerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("/userauth/register2/")
    suspend fun signUp(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("/userauth/login2/")
    suspend fun signIn(@Body userRequest: UserRequest): Response<UserResponse>
    @POST("userauth/generateotp2")
    suspend fun generateOtp(@Body userOtpGenerateRequest: UserOtpGenerateRequest):Response<UserOtpGenerateResponse>

    @POST("userauth/verifyotp2")
    suspend fun verifyOwnerOtp(@Body userOtpGenerateResponse: UserOtpGenerateResponse):Response<UserVerifyOtpResponse>

    @POST("userauth/savepassword2")
    suspend fun createOwnerPassword(@Body user: ConfirmOwnerPasswordRequest): Response<UserSavePasswordResponse>

}