package com.example.hotelreservationsystem.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
@Parcelize
data class Room(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val hotel: String,
    val img: String,
    val number: Int,
    val owner: String,
    val price: Int,
    val status: Boolean,
    val type: String,
    val updatedAt: String
):Parcelable,Serializable