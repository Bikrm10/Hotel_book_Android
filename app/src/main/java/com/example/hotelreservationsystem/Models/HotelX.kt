package com.example.hotelreservationsystem.Models

import java.io.Serializable

data class HotelX(
    val __v: Int,
    val _id: String,
    val address: String,
    val createdAt: String,
    val description: String,
    val name: String,
    val owner: String,
    val photos: List<String>,
    val review: List<Any>,
    val rooms: List<String>,
    val updatedAt: String
):Serializable