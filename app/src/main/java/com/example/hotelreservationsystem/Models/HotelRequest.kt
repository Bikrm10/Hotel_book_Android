package com.example.hotelreservationsystem.Models

data class HotelRequest(
    val address: String,
    val description: String,
    val name: String,
    val photos: String,
    val type:Int,
    val cheapestPrice:Int
)
