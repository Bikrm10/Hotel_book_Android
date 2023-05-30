package com.example.hotelreservationsystem.Models

data class UserX(
    val __v: Int,
    val _id: String,
    val booking: List<String>,
    val createdAt: String,
    val email: String,
    val isOwner: Boolean,
    val password: String,
    val review: List<Any>,
    val updatedAt: String
)