package com.example.hotelreservationsystem.api

import com.example.hotelreservationsystem.Models.BookRequest
import com.example.hotelreservationsystem.Models.FinalBookingResponse
import com.example.hotelreservationsystem.Models.HotelResponse
import com.example.hotelreservationsystem.Models.HotelResponseListMethod
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface getallHotelsApi {

   @GET("user/getallhotel/{userId}")
   suspend fun getallHotels(@Path ("userId")userId:String): Response<HotelResponseListMethod>


   // yaha  lekh xu man sab bookigs ko code because yasley malae interceptors bala retrofit client di rahexa

   // writing functions to book the room
//https://anxious-gaiters-bee.cyclic.app/user/bookroom/647034fa7f9bac4c2cb46119/646da91c4cd9ef9e30a5ad4a/646deb68e1b59be01864c43d
    @POST("user/bookroom/{userId}/{hotelId}/{roomId}")
    suspend fun bookRoom(@Path("userId")userId: String,@Path("hotelId")hotelId:String,@Path("roomId")roomId:String,@Body bookRequest: BookRequest):Response<HotelResponse>

    //interface to get bookings of users


//https://anxious-gaiters-bee.cyclic.app/user/getallbooking/647034fa7f9bac4c2cb46119
    @GET("user/getallbooking/{userId}")
    suspend fun userBookings(@Path("userId")userId:String):Response<FinalBookingResponse>


    @POST("user/cancelbooking/{userId}/{hotelId}/{roomId}/{bookingId}")
   suspend fun cancelBooking(@Path("userId")userId: String,
                             @Path("hotelId")hotelId: String,
                             @Path("roomId")roomId: String,
                             @Path("bookingId")bookingId:String):Response<FinalBookingResponse>



}