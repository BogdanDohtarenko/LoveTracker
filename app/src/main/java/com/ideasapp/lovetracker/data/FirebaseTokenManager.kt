package com.ideasapp.lovetracker.data

import com.google.auth.oauth2.GoogleCredentials
import java.io.FileInputStream
import java.io.InputStream
import android.content.Context

object FirebaseTokenManager {

    private const val SCOPES = "https://www.googleapis.com/auth/firebase.messaging"

    fun getAccessToken(context: Context): String {
        val inputStream: InputStream = context.assets.open("lovetracker-ac855-firebase-adminsdk-dr6s6-95ca04612d.json")
        val credentials = GoogleCredentials
            .fromStream(inputStream)
            .createScoped(listOf(SCOPES))
        credentials.refreshIfExpired()
        return credentials.accessToken.tokenValue

    }
}