package com.example.hotelreservationsystem.Models

import java.io.Serializable

data class Owner(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val hotel: List<String>,
    val isOwner: Boolean,
    val ownername: String,
    val password: String,
    val updatedAt: String
):Serializable