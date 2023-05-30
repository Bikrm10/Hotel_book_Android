package com.example.hotelreservationsystem.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelreservationsystem.Models.BookRequest
import com.example.hotelreservationsystem.Models.FinalBookingResponse
import com.example.hotelreservationsystem.Models.HotelResponseListMethod
import com.example.hotelreservationsystem.Repositories.GetAllRepository
import com.example.hotelreservationsystem.utils.NetworkResult
import com.example.hotelreservationsystem.utils.constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAllHotelViewModel @Inject constructor(  val getAllRepository: GetAllRepository):ViewModel() {
     val _hotelLiveDataList : LiveData<NetworkResult<HotelResponseListMethod>>
        get() = getAllRepository.hotelLiveDataList



    val userBookingsLiveData :LiveData<NetworkResult<FinalBookingResponse>>
            get()= getAllRepository.userBookLiveData

     fun getAllHotel(userId: String) {
         Log.d(TAG,"view model ko function call vayo ")
         viewModelScope.launch {
             getAllRepository.getAllHotel(userId)
         }

    }
    fun bookRoom(userId: String,hotelId:String,roomId:String,bookRequest:BookRequest)
    {
        Log.d(TAG,"view model ko function call vayo ")
        viewModelScope.launch {
            getAllRepository.bookRoom(userId,hotelId,roomId,bookRequest)
        }
    }
    fun userBookingd (userId: String)
    {
        viewModelScope.launch {
            getAllRepository.userBookings(userId)
        }
    }
    fun calledFunction ()
    {
        Log.d(TAG,"Sucessfully called the data")
    }

    fun cancelBooking(userId: String,hotelId: String,roomId: String,bookingId:String)
    {
        viewModelScope.launch {
            getAllRepository.cancelBooking(userId,hotelId,roomId,bookingId)
        }
    }

}
