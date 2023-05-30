package com.example.hotelreservationsystem.Models

import java.io.Serializable

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val booking: List<String>,
    val isOwner: Boolean,
    val password: String,
    val review: List<Any>,
    val updatedAt: String,
    val username: String
):Serializable