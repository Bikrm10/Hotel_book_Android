package com.example.hotelreservationsystem.Models

data class ConfirmOwnerPasswordRequest(
    val confirmPassword: String,
    val email: String,
    val password: String
)