package com.example.hotelreservationsystem.utils

import android.content.Context
import androidx.compose.ui.layout.ContentScale
import com.example.hotelreservationsystem.utils.constants.SHARED_PREF_TOKEN_FILE
import com.example.hotelreservationsystem.utils.constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences(SHARED_PREF_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()

    }

    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

}