package com.example.hotelreservationsystem.Models

data class BookingX(
    val __v: Int,
    val _id: String,
    val endDate: String,
    val hotel: HotelXX,
    val isActive: Boolean,
    val room: RoomX,
    val startDate: String,
    val user: UserXX
)