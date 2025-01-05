package com.ideasapp.lovetracker.data

import com.google.auth.oauth2.GoogleCredentials
import java.io.FileInputStream
import java.io.InputStream
import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

object FirebaseTokenManager {

    private const val SCOPES = "https://www.googleapis.com/auth/firebase.messaging"

    fun getAccessToken(context: Context): String {
        return try {
            val inputStream: InputStream = context.assets.open("lovetracker-ac855-firebase-adminsdk-dr6s6-186864ca8d.json")

            val credentials = GoogleCredentials
                .fromStream(inputStream)
                .createScoped(listOf(SCOPES))
            credentials.refreshIfExpired()

            credentials.accessToken.tokenValue
        } catch (e: Exception) {
            Log.e("FСM", "Error getting access token: ", e)
            throw e
        }
    }
}