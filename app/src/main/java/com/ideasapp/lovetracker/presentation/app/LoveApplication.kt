package com.ideasapp.lovetracker.presentation.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class LoveApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            // Log all Firebase apps
            val apps = FirebaseApp.getApps(this)
            if (apps.isEmpty()) {
                FirebaseApp.initializeApp(this)
                Log.d("FCM", "Firebase initialized successfully")
            } else {
                for (app in apps) {
                    Log.d("FCM", "Found Firebase app: ${app.name}")
                }
            }
        } catch (e: Exception) {
            Log.e("FCM", "Error initializing Firebase", e)
        }
    }
}