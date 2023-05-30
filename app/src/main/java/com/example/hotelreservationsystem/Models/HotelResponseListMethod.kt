package com.example.hotelreservationsystem.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelResponseListMethod(
    val hotel: List<Hotel>
):Parcelable