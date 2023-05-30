package com.example.hotelreservationsystem.Models

data class OwnerX(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val hotel: List<String>,
    val isOwner: Boolean,
    val ownername: String,
    val password: String,
    val updatedAt: String
)