package com.example.hotelreservationsystem.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Parcelize
data class OwnerResponse(
    val access_token: String,
    var owner:@RawValue Owner
):Parcelable, Serializable