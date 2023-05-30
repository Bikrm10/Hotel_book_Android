package com.example.hotelreservationsystem.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable
@Parcelize
data class Hotel(
    val __v: Int,
    val _id: String,
    val address: String,
    val createdAt: String,
    val description: String,
    val rating:Int,
    val type :String,
    val cheapestPrice:Int,
    val name: String,
    val owner: Owner,
    val user: User,
    val photos: List<String>,
    val review: @RawValue List<Any>,
    val rooms: List<Room>,
    val updatedAt: String

):Parcelable,Serializable