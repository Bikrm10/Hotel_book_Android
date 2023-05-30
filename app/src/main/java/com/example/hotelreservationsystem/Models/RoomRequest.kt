package com.example.hotelreservationsystem.Models

data class RoomRequest(
    val number: Int,
    val price: Int,
    val type: String,
    val url: String
)